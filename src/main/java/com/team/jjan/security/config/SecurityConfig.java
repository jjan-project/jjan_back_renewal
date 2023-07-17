package com.team.jjan.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.jjan.common.ResponseMessage;
import com.team.jjan.jwt.filter.JwtAuthenticationFilter;
import com.team.jjan.jwt.support.JwtProvider;
import com.team.jjan.user.entitiy.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static com.team.jjan.common.ResponseCode.AUTHORIZATION_FAIL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .requestMatchers(HttpMethod.GET , "/**").permitAll()
                .requestMatchers(HttpMethod.POST , "/api/user/login" , "/api/user/join").permitAll()
                .requestMatchers(HttpMethod.DELETE , "/**").hasRole(Role.MEMBER.name())
                .requestMatchers(HttpMethod.PATCH , "/**").hasRole(Role.MEMBER.name())
                .requestMatchers(HttpMethod.PUT , "/**").hasRole(Role.MEMBER.name())
                .requestMatchers(HttpMethod.POST , "/**").hasRole(Role.MEMBER.name())
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("refreshToken")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/api/security/authentication")
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        setErrorResponse(response , HttpStatus.UNAUTHORIZED.value());
                    }
                });

        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public static void setErrorResponse(HttpServletResponse response, int status) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(status);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseMessage errorResponse = ResponseMessage.of(AUTHORIZATION_FAIL);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
