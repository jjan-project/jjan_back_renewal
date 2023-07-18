package com.team.jjan.jwt.support;

import com.team.jjan.jwt.dto.Token;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

public class JwtCookie {

    @Value("${server.url}")
    private static String DOMAIN_URL;

    public static long ACCESS_TOKEN_MAX_AGE = 1000 * 60 * 30;

    public static long REFRESH_TOKEN_MAX_AGE = 1000L * 60 * 60 * 24 * 30;

    public static ResponseCookie createAccessToken(String accessToken) {
        return ResponseCookie.from("accessToken" , accessToken)
                .path("/")
                .maxAge(ACCESS_TOKEN_MAX_AGE)
                //.secure(true)
                .domain(DOMAIN_URL)
                //.httpOnly(true)
                //.sameSite("none")
                .build();
    }

    public static ResponseCookie createRefreshToken(String refreshToken) {
        return ResponseCookie.from("refreshToken" , refreshToken)
                .path("/")
                .maxAge(REFRESH_TOKEN_MAX_AGE)
                //.secure(true)
                .domain(DOMAIN_URL)
                //.httpOnly(true)
                //.sameSite("none")
                .build();
    }

    public static void setCookieFromJwt(HttpServletResponse response , Token token) {
        response.addHeader("Set-Cookie" , createAccessToken(token.getAccessToken()).toString());
        response.addHeader("Set-Cookie" , createRefreshToken(token.getRefreshToken()).toString());
    }

    public static void deleteJwtTokenInCookie(HttpServletResponse response) {
        Cookie accessToken = new Cookie("accessToken", null);
        accessToken.setPath("/");
        accessToken.setMaxAge(0);
        accessToken.setDomain(DOMAIN_URL);

        Cookie refreshToken = new Cookie("refreshToken", null);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(0);
        refreshToken.setDomain(DOMAIN_URL);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);
    }
}