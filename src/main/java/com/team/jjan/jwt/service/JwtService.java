package com.team.jjan.jwt.service;

import com.team.jjan.common.ResponseMessage;
import com.team.jjan.jwt.domain.RefreshToken;
import com.team.jjan.jwt.exception.AuthenticationException;
import com.team.jjan.jwt.exception.TokenForgeryException;
import com.team.jjan.jwt.repository.RefreshTokenRepository;
import com.team.jjan.jwt.support.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static com.team.jjan.common.ResponseCode.CREATE_ACCESS_TOKEN;
import static com.team.jjan.jwt.support.JwtCookie.createAccessToken;
import static com.team.jjan.jwt.support.JwtCookie.deleteJwtTokenInCookie;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken getRefreshToken(HttpServletRequest request) throws AuthenticationException {
        String refreshToken = getRefreshTokenFromHeader(request);

        return refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new TokenForgeryException("알 수 없는 RefreshToken 입니다."));
    }

    public ResponseMessage reissueAccessToken(HttpServletRequest request , HttpServletResponse response)
            throws AuthenticationException {
        try {
            RefreshToken token = getRefreshToken(request);
            String accessToken = jwtTokenProvider.validateRefreshToken(token);

            response.addHeader("Set-Cookie" , createAccessToken(accessToken).toString());

            return ResponseMessage.of(CREATE_ACCESS_TOKEN);
        } catch (NoSuchElementException e) {
            deleteJwtTokenInCookie(response);

            throw new TokenForgeryException("변조된 RefreshToken 입니다.");
        }
    }

    public String getRefreshTokenFromHeader(HttpServletRequest request) throws AuthenticationException {
        Cookie cookies[] = request.getCookies();

        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("refreshToken")).findFirst().map(Cookie::getValue)
                    .orElseThrow(() -> new AuthenticationException("인증되지 않은 사용자입니다."));
        }

        throw new AuthenticationException("인증되지 않은 사용자입니다.");
    }
}
