package com.team.jjan.jwt.filter;

import com.team.jjan.jwt.support.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.team.jjan.jwt.exception.AuthenticationException;
import com.team.jjan.jwt.exception.SessionExpireException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getAccessTokenFromHeader(request);

        if (jwtProvider.validateAccessToken(token)) {
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    public String getAccessTokenFromHeader(HttpServletRequest request) {
        Cookie cookies[] = request.getCookies();
        String accessToken = null;

        if (cookies != null && cookies.length != 0) {
            accessToken = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("accessToken")).findFirst().map(Cookie::getValue)
                    .orElse(null);
        }

        return accessToken;
    }

}
