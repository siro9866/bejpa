package com.sil.bejpa.common.jwt;


import com.sil.bejpa.common.exception.GlobalExceptionHandler;
import com.sil.bejpa.common.response.ResponseCode;
import com.sil.bejpa.common.util.UtilMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final UtilMessage utilMessage;
	/**
	 * 인가 실패 관련 403 핸들링
	 * @param request ServletRequest 객체
	 * @param response ServletResponse 객체
	 * @param accessDeniedException 접근권한 거부 예외 정보
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
		log.debug("::::::::::::::::::::::::::::::JwtAccessDeniedHandler(인가실패)::::::::::::::::::::::::::::");
		GlobalExceptionHandler.filterExceptionHandler(response, HttpStatus.FORBIDDEN, ResponseCode.ACCESS_DENIED, utilMessage.getMessage("access.denied", null));
	}
}