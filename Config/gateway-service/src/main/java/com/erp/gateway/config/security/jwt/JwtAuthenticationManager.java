package com.erp.gateway.config.security.jwt;

import com.erp.gateway.error.exceptions.RequestException;
import com.erp.gateway.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import com.erp.gateway.repository.UserRepository;


@Component
@AllArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtService.extractAllClaims(auth.getCredentials().toString()))
                .onErrorResume(e -> Mono.error(new RequestException("bad token", "400-Bad Request")))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(claims ->
                        Mono.just(new UsernamePasswordAuthenticationToken(
                                claims.getSubject(),
                                null,
                                userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new RequestException("asgsag", "asg")).getAuthorities())));
    }
}
