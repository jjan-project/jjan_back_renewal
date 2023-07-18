package com.team.jjan.jwt.filter;

import com.team.jjan.jwt.support.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.team.jjan.jwt.exception.AuthenticationException;
import com.team.jjan.jwt.exception.SessionExpireException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.team.jjan.jwt.support.JwtCookie.deleteJwtTokenInCookie;
import static com.team.jjan.security.support.CustomAuthenticationEntryPoint.setErrorResponse;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean{

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getAccessTokenFromHeader((HttpServletRequest) request);

        if (jwtProvider.validateAccessToken(token)) {
            try {
                setAuthentication(token);
            } catch (UsernameNotFoundException e) {
                deleteJwtTokenInCookie((HttpServletResponse) response);

                setErrorResponse((HttpServletResponse) response , HttpServletResponse.SC_UNAUTHORIZED , "토큰이 변조되었거나 유효하지 않습니다.");
            }
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

        if (cookies != null && cookies.length != 0) {
            accessToken = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("accessToken")).findFirst().map(Cookie::getValue)
                    .orElse(null);
        }

        return accessToken;
    }

}
