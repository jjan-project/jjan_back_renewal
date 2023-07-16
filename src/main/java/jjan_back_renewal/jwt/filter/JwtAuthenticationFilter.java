package jjan_back_renewal.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jjan_back_renewal.jwt.exception.AuthenticationException;
import jjan_back_renewal.jwt.exception.SessionExpireException;
import jjan_back_renewal.jwt.support.JwtProvider;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.resolveToken(request);
        String refreshToken = getRefreshTokenFromCookies(request);

        if (accessToken != null && jwtProvider.validateAccessToken(accessToken)) {
            resetUserAuthentication(accessToken.split(" ")[1].trim());
        } else if (refreshToken != null && jwtProvider.validateRefreshToken(refreshToken)) {
            accessToken = jwtProvider.createToken(refreshToken);
            response.addHeader("Authorization", "Bearer " + accessToken);
            if (jwtProvider.refreshTokenExpiresLessThan1Month(refreshToken)) {
                issueRefreshToken(response , refreshToken);
            }
            resetUserAuthentication(accessToken);
        } else {
            throw new SessionExpireException("사용자 세션이 만료되었습니다.");
        }
        filterChain.doFilter(request, response);
    }

    public void issueRefreshToken(HttpServletResponse response, String refreshToken) {
        Cookie newRefreshToken = new Cookie("refresh_token", refreshToken);
        newRefreshToken.setMaxAge(60 * 60 * 24 * 90);
        response.addCookie(newRefreshToken);
    }

    public void resetUserAuthentication(String accessToken) {
        Authentication auth = jwtProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public String getRefreshTokenFromCookies(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        String refreshToken = "";

        if (cookies != null) {
            refreshToken = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("refresh_token")).findFirst().map(Cookie::getValue)
                    .orElseThrow(() -> new AuthenticationException("인증되지 않은 사용자입니다."));
        }

        return refreshToken;
    }
}