package com.team.jjan.security.support;

import com.team.jjan.jwt.exception.TokenForgeryException;
import com.team.jjan.jwt.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.team.jjan.common.ResponseCode.CREATE_ACCESS_TOKEN;
import static com.team.jjan.jwt.support.JwtCookie.deleteJwtTokenInCookie;
import static com.team.jjan.security.support.ExceptionHandlerFilter.setErrorResponse;
import static com.team.jjan.security.support.ExceptionHandlerFilter.setSuccessResponse;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JwtService jwtService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        try {
            jwtService.reissueAccessToken(request , response);

            setSuccessResponse(response , CREATE_ACCESS_TOKEN);
        } catch (TokenForgeryException e) {
            deleteJwtTokenInCookie(response);

            setErrorResponse(response , HttpStatus.UNAUTHORIZED.value() , e.getMessage());
        }

    }

}
