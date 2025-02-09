package com.sil.bejpa.token.service;

import com.sil.bejpa.common.exception.CustomException;
import com.sil.bejpa.common.jwt.JwtUtil;
import com.sil.bejpa.common.response.ResponseCode;
import com.sil.bejpa.common.util.UtilCommon;
import com.sil.bejpa.common.util.UtilMessage;
import com.sil.bejpa.member.repository.MemberRepository;
import com.sil.bejpa.token.dto.TokenCreateDto;
import com.sil.bejpa.token.repository.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-01-21
 * Description :
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class TokenService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final UtilMessage utilMessage;

    @Value("${spring.jwt.access.expiration}") Long JWT_ACCESS_EXPIRATION;
    @Value("${spring.jwt.refresh.expiration}") Long JWT_REFRESH_EXPIRATION;

    /**
     * 토큰 재발행
     */
    public Map<String, String> reissue(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 쿠키에 생성한 refresh 토큰 가져온다
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if(UtilCommon.isNotEmpty(cookies)) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        // 토큰이 있는지 확인
        if(UtilCommon.isEmpty(refreshToken)) {
            // response status code
//            throw new CustomException(HttpStatus.BAD_REQUEST, utilMessage.getMessage("jwt.refreshToken.empty", null));
            throw new CustomException(ResponseCode.JWT_REFRESHTOKEN_EMPTY, utilMessage.getMessage("jwt.refreshToken.empty", null));
        }

        // 토큰이 만료되었는지 확인
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
//            throw new CustomException(HttpStatus.BAD_REQUEST, utilMessage.getMessage("jwt.refreshToken.expired", null));
            throw new CustomException(ResponseCode.JWT_REFRESHTOKEN_EXPIRED, utilMessage.getMessage("jwt.refreshToken.expired", null));
        }

        // 토큰이 refresh인지 확인(발급시 페이로드에 명시해뒀음)
        String tokenCategory = jwtUtil.getTokenCategory(refreshToken);
        if(!"refreshToken".equals(tokenCategory)) {
//            throw new CustomException(HttpStatus.BAD_REQUEST, utilMessage.getMessage("jwt.refreshToken.invalid", null));
            throw new CustomException(ResponseCode.JWT_REFRESHTOKEN_INVALID, utilMessage.getMessage("jwt.refreshToken.invalid", null));
        }

        // DB에 해당 refresh 토큰이 있는지 확인
        Boolean isExist = tokenRepository.existsByRefreshToken(refreshToken);
        if(!isExist) {
//            throw new CustomException(HttpStatus.BAD_REQUEST, utilMessage.getMessage("jwt.refreshToken.nodata", null));
            throw new CustomException(ResponseCode.JWT_REFRESHTOKEN_NODATA, utilMessage.getMessage("jwt.refreshToken.nodata", null));
        }

        String memberId = jwtUtil.getMemberId(refreshToken);
        String meberRole = jwtUtil.getMeberRole(refreshToken);

        // 새로운 토큰 생성
        String newAccessToken = jwtUtil.createJwt("accessToken", memberId, meberRole, JWT_ACCESS_EXPIRATION * 1000L);
        String newRefreshToken = jwtUtil.createJwt("refreshToken", memberId, meberRole, JWT_REFRESH_EXPIRATION * 1000L);

        // Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        tokenRepository.deleteByRefreshToken(refreshToken);

        TokenCreateDto refreshCreateDto  = new TokenCreateDto();
        refreshCreateDto.setMemberId(memberId);
        refreshCreateDto.setRefreshToken(newRefreshToken);
        refreshCreateDto.setRefreshTokenExpiration(LocalDateTime.now().plusSeconds(JWT_REFRESH_EXPIRATION));
        tokenRepository.save(refreshCreateDto.toEntity());

        // 응답
        Map<String, String> map = new HashMap<String, String>();
        map.put("accessToken", newAccessToken);
        map.put("refreshToken", newRefreshToken);
        return map;

    }
}
