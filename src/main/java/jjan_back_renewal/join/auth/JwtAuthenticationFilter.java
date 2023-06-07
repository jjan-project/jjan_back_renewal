package jjan_back_renewal.join.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * Jwt가 유효성을 검증하는 Filter
 */

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.resolveToken(request);
        String refreshToken = getRefreshTokenFromCookies(request);
        if (accessToken != null && jwtProvider.validateAccessToken(accessToken)) {
            log.info("access token 유효, 요청 진행");
            accessToken = accessToken.split(" ")[1].trim();
            Authentication auth = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else if (refreshToken != null && jwtProvider.validateRefreshToken(refreshToken)) {
            log.info("refresh token 유효, 요청 진행");
            accessToken = jwtProvider.createToken(refreshToken);
            response.addHeader("Authorization", "Bearer " + accessToken);
            if (jwtProvider.refreshTokenExpiresLessThan1Month(refreshToken)) {
                log.info("refresh token 곧 만료 예정이라 새롭게 발급");
                Cookie newRefreshToken = new Cookie("refresh_token", refreshToken);
                newRefreshToken.setMaxAge(60 * 60 * 24 * 90);
                response.addCookie(newRefreshToken);
            }
            Authentication auth = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            log.info("refresh token 만료되었음. 재로그인 필요.");
        }
        filterChain.doFilter(request, response);
    }

    public String getRefreshTokenFromCookies(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                String name = c.getName();
                String value = c.getValue();
                if (name.equals("refresh_token")) {
                    return value;
                }
            }
        }
        return null;
    }
}