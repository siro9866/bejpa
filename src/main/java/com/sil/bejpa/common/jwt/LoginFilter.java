package com.sil.bejpa.common.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sil.bejpa.common.exception.GlobalExceptionHandler;
import com.sil.bejpa.common.response.ResponseCode;
import com.sil.bejpa.common.util.UtilCommon;
import com.sil.bejpa.common.util.UtilMessage;
import com.sil.bejpa.common.util.UtilProperty;
import com.sil.bejpa.member.dto.MemberLoginDto;
import com.sil.bejpa.token.dto.TokenCreateDto;
import com.sil.bejpa.token.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    // 로그인 검증을 담당
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;
    private final UtilMessage utilMessage;

    private final Long JWT_ACCESS_EXPIRATION = Long.valueOf(UtilProperty.getProperty("spring.jwt.access.expiration"));
    private final Long JWT_REFRESH_EXPIRATION = Long.valueOf(UtilProperty.getProperty("spring.jwt.refresh.expiration"));

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, TokenRepository tokenRepository, ObjectMapper objectMapper, UtilMessage utilMessage) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
        this.objectMapper = objectMapper;
        this.utilMessage = utilMessage;
    }

    /**
     * 로그인을 위한 인증
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 클라이언트 요청에서 id, password 추출
        String memberId = "";
        String password = "";

        try {
            // 로그인시 입력받을 필드
            MemberLoginDto loginDto = objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), MemberLoginDto.class);
            memberId = loginDto.getMemberId();
            password = loginDto.getPassword();
            log.debug("로그인정보:{}", loginDto);
        } catch (JsonMappingException e) {
            log.error("이건 언제터지지(JsonMappingException)");
        } catch (JsonProcessingException e) {
            log.error("이건 언제터지지(JsonProcessingException)");
        } catch (IOException e) {
            log.error("이건 언제터지지(IOException)");
        }


        // 시큐리티에서 id, pw 를 검증하기 위해서는 token에 담아야 함
        log.debug("before: {}", memberId + ":" + password);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(memberId, password);
        log.debug("after: {}", authToken);

        // 토큰에 담은 값을 검증 하기위해 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    /**
     * 로그인 성공시실행하는 메서드(여기서 JWT를 발급하면 됨)
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        // 유저정보
        String memberId = authResult.getName();

        // role
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String meberRole = auth.getAuthority();

        // 토큰생성
        String accessToken = jwtUtil.createJwt("accessToken", memberId, meberRole, JWT_ACCESS_EXPIRATION * 1000L);
        String refreshToken = jwtUtil.createJwt("refreshToken", memberId, meberRole, JWT_REFRESH_EXPIRATION * 1000L);

        // refresh 토큰 삭제
        tokenRepository.deleteByMemberId(memberId);

        // refresh 토큰 저장
        TokenCreateDto refreshInsertDto = new TokenCreateDto();
        refreshInsertDto.setMemberId(memberId);
        refreshInsertDto.setRefreshToken(refreshToken);
        refreshInsertDto.setRefreshTokenExpiration(LocalDateTime.now().plusSeconds(JWT_REFRESH_EXPIRATION));
        tokenRepository.save(refreshInsertDto.toEntity());

        // 응답설정
        response.setHeader("accessToken", accessToken);                    // access 토큰은 헤더로 내려줌
        response.addCookie(UtilCommon.createCookie("refreshToken", refreshToken));    // refresh 토큰은 쿠키에 저장


        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("data", null);

        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(responseBody));
        writer.flush();
        writer.close();

    }

    /**
     * 로그인 실패시 실행하는 메서드
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        GlobalExceptionHandler.filterExceptionHandler(response, HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE, ResponseCode.LOGIN_FAIL, utilMessage.getMessage("login.fail", null));
    }

}
