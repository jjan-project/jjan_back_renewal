package com.team.jjan.jwt.filter;

import com.team.jjan.jwt.service.JwtService;
import com.team.jjan.jwt.support.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

import static com.team.jjan.common.ResponseCode.AUTHENTICATION_ERROR;
import static com.team.jjan.common.ResponseCode.CREATE_ACCESS_TOKEN;
import static com.team.jjan.security.support.ExceptionHandlerFilter.setErrorResponse;
import static com.team.jjan.security.support.ExceptionHandlerFilter.setSuccessResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean{

    private final JwtProvider jwtProvider;
    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Cookie cookies[] = ((HttpServletRequest) request).getCookies();

        if (cookies != null && cookies.length != 0) {
            String token = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("accessToken")).findFirst().map(Cookie::getValue)
                    .orElse(null);

            if (token == null || token.isBlank() || token.isEmpty()) {
                setErrorResponse(((HttpServletResponse) response) , AUTHENTICATION_ERROR.getCode() , "인증이 필요한 요청입니다.");
            } else if (jwtProvider.validateAccessToken(token)) {
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (token != null) {
                jwtService.reissueAccessToken(((HttpServletRequest) request), ((HttpServletResponse) response));

                setSuccessResponse(((HttpServletResponse) response) , CREATE_ACCESS_TOKEN);
                return;
            }
        }

        chain.doFilter(request, response);
    }

}
