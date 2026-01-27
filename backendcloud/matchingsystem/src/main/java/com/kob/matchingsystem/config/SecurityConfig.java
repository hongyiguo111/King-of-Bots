package com.kob.matchingsystem.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final IpAddressMatcher IPV4_LOCALHOST = new IpAddressMatcher("127.0.0.1");
    private static final IpAddressMatcher IPV6_LOCALHOST = new IpAddressMatcher("::1");

    private boolean isLocalhost(HttpServletRequest request) {
        return IPV4_LOCALHOST.matches(request) || IPV6_LOCALHOST.matches(request);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/player/add/", "/player/remove/")
                        .access((authentication, context) ->
                                new AuthorizationDecision(isLocalhost(context.getRequest())))
                        .anyRequest().denyAll()  // 拒绝所有其他请求
                );

        return http.build();
    }
}