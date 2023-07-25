package com.team.jjan.jwt.filter;

import com.team.jjan.jwt.support.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean{

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getAccessTokenFromHeader((HttpServletRequest) request);

        if (jwtProvider.validateAccessToken(token)) {
            setAuthentication(token);
        }

        chain.doFilter(request, response);
    }

    public void setAuthentication(String token) {
        Authentication authentication = jwtProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public String getAccessTokenFromHeader(HttpServletRequest request) {
        Cookie cookies[] = request.getCookies();
        String accessToken = null;

        if (cookies != null) {
            accessToken = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("accessToken")).findFirst().map(Cookie::getValue)
                    .orElse(null);
        }

        return accessToken;
    }

}
