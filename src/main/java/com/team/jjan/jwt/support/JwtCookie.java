package com.team.jjan.jwt.support;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class JwtCookie {

    public static void setRefreshTokenInCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 90);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

}
