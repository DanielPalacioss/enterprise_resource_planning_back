package com.erp.gateway.config.security.jwt;

import com.erp.gateway.error.exceptions.RequestException;
import com.erp.gateway.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private WebClient webClient;

    @Autowired
    private JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        webClient.get()
                .uri("/user/getAuthorities/" + "")
                .retrieve()
                .bodyToMono(Collection.class);

        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return Mono.just(authentication)
                .publishOn(Schedulers.boundedElastic())
                .mapNotNull(auth -> jwtService.extractAllClaims(authentication.getCredentials().toString()))
                .log()
                .onErrorResume(e -> Mono.error(new RequestException("bad token", "400-Bad Request")))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(claims -> webClient.get()
                        .uri("/user/getAuthorities/" + claims.getSubject())
                        .retrieve()
                        .bodyToMono(Collection.class)) // Mono<Collection<? extends GrantedAuthority>>
                .map(authorities -> new UsernamePasswordAuthenticationToken(
                        "",
                        null,
                        authorities));
}
}
