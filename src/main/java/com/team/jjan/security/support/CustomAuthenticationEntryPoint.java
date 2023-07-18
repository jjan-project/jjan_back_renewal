package com.team.jjan.security.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.jjan.common.ResponseCode;
import com.team.jjan.common.ResponseMessage;
import com.team.jjan.jwt.exception.TokenForgeryException;
import com.team.jjan.jwt.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.team.jjan.common.ResponseCode.AUTHENTICATION_ERROR;
import static com.team.jjan.common.ResponseCode.CREATE_ACCESS_TOKEN;
import static com.team.jjan.jwt.support.JwtCookie.deleteJwtTokenInCookie;

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
        } catch (Exception e) {
            setErrorResponse(response , HttpServletResponse.SC_UNAUTHORIZED , "인증되지 않은 사용자입니다.");
        }
    }

    public static void setErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(status);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseMessage errorResponse = ResponseMessage.of(AUTHENTICATION_ERROR , message);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    public static void setSuccessResponse(HttpServletResponse response , ResponseCode responseCode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseMessage errorResponse = ResponseMessage.of(responseCode);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
