package com.erp.gateway.config.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
public class GatewayAuthBasicFilter implements GatewayFilter {

    @Value("${service.security.user.name}")
    private String username;

    @Value("${service.security.user.password}")
    private String password;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (username == null || password == null) {
            return Mono.error(new RuntimeException("Credenciales no válidas auth basic"));
        }

        String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        String authHeader = "Basic " + encodedCredentials;

        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("Authorization", authHeader)
                .build();

        return chain.filter(exchange.mutate().request(request).build());
    }
}
