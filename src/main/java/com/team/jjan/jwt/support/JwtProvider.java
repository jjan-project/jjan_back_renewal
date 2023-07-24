package com.team.jjan.jwt.support;

import com.team.jjan.jwt.domain.RefreshToken;
import com.team.jjan.jwt.dto.Token;
import com.team.jjan.security.service.UserDetailService;
import com.team.jjan.user.entitiy.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.team.jjan.jwt.support.JwtCookie.ACCESS_TOKEN_MAX_AGE;
import static com.team.jjan.jwt.support.JwtCookie.REFRESH_TOKEN_MAX_AGE;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final UserDetailService userDetailService;

    public Token createJwtToken(String email, Role roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);

        String accessToken = createAccessToken(claims);
        String refreshToken = createRefreshToken(claims);

        return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(email).build();
    }

    public String createAccessToken(Claims claims) {
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_MAX_AGE))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(Claims claims) {
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_MAX_AGE))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(getUserEmail(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserEmail(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        UserDetails userDetails = userDetailService.loadUserByUsername(getAccount(token));

        return userDetails.getUsername();
    }

    public String getAccount(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateAccessToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String validateRefreshToken(RefreshToken refreshToken) {
        String token = refreshToken.getToken();

        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        if (!claims.getBody().getExpiration().before(new Date())) {
            return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
        }

        return null;
    }

    public String recreationAccessToken(String userEmail, Object roles) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("roles", roles);

        return createAccessToken(claims);
    }

}