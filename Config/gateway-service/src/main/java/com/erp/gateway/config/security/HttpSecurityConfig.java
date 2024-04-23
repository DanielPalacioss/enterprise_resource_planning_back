package com.erp.gateway.config.security;

import com.erp.gateway.config.security.jwt.SecurityContextRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class HttpSecurityConfig {

    private SecurityContextRepository securityContextRepository;
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                //.requestCache(requestCache -> requestCache.requestCache(NoOpServerRequestCache.getInstance()))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.GET, "ac/auth/authenticate").permitAll()
                        .pathMatchers("/error").permitAll()
                        .pathMatchers(HttpMethod.GET, "ac/auth/publico").hasRole("ADMINISTRATOR")
                        .pathMatchers(HttpMethod.GET, "sm/customer/**").hasRole("CUSTOMER")
                        .pathMatchers("sm/**").hasRole("ADMINISTRATOR")
                        .pathMatchers("ac/**").permitAll()
                        .anyExchange().denyAll())
                .securityContextRepository(securityContextRepository)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .build();
    }
}


