package com.team.jjan.security.config;

import com.team.jjan.jwt.filter.JwtAuthenticationFilter;
import com.team.jjan.security.support.ExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .anyRequest().permitAll()
//                .requestMatchers(HttpMethod.POST , "/api/user/**").permitAll()
//                .requestMatchers(HttpMethod.DELETE , "/api/user/**").permitAll()
//                .requestMatchers(HttpMethod.DELETE , "/**").hasRole(Role.MEMBER.name())
//                .requestMatchers(HttpMethod.PATCH , "/**").hasRole(Role.MEMBER.name())
//                .requestMatchers(HttpMethod.PUT , "/**").hasRole(Role.MEMBER.name())
//                .requestMatchers(HttpMethod.POST , "/**").hasRole(Role.MEMBER.name())
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("accessToken")
                .deleteCookies("refreshToken");

        http.addFilterBefore(new ExceptionHandlerFilter() , UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
