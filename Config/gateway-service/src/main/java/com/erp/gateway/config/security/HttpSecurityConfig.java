package com.erp.gateway.config.security;

import com.erp.gateway.config.security.filter.JwtAuthenticationFilter;
import com.erp.gateway.config.security.jwt.SecurityContextRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class HttpSecurityConfig {

    private JwtAuthenticationFilter authenticationFilter;

    private SecurityContextRepository securityContextRepository;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                //.requestCache(requestCache -> requestCache.requestCache(NoOpServerRequestCache.getInstance()))
                .securityContextRepository(securityContextRepository)
                .addFilterAfter(authenticationFilter, SecurityWebFiltersOrder.FIRST)
                .exceptionHandling(exceptionHandlingSpec -> {
                    exceptionHandlingSpec.authenticationEntryPoint((exchange, exception) -> Mono.error(exception))
                            .accessDeniedHandler((exchange, exception) -> Mono.error(exception));
                })
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.GET, "/auth/authenticate").permitAll()
                        .pathMatchers(HttpMethod.GET, "/auth/public").permitAll()
                        .pathMatchers("/error").permitAll()
                        .pathMatchers(HttpMethod.GET, "auth/publico").hasAuthority("ALL_SALESMANAGEMENT")
                        // .pathMatchers(HttpMethod.GET, "sm/customer/**").hasAuthority("READ_CUSTOMER")
                        .pathMatchers("/sm/**").hasAuthority("ALL_SALESMANAGEMENT")
                        .anyExchange().denyAll()
                )
                .build();
    }
}


