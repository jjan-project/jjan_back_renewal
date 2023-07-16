package com.team.jjan.jwt.support;

import com.team.jjan.user.entitiy.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;

import static com.team.jjan.jwt.support.JwtProvider.ACCESS_TOKEN_EXPIRE_TIME;

public class JwtCookie {

    public static void setRefreshTokenInCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("refresh_token", token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 90);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

}
