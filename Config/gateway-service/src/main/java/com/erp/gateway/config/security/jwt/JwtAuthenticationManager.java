package com.erp.gateway.config.security.jwt;

import com.erp.gateway.error.exceptions.RequestException;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;
import java.util.List;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    @LoadBalanced
    private final RestTemplate restTemplate = new RestTemplate();
    ParameterizedTypeReference<Collection<GrantedAuthority>> typeRef = new ParameterizedTypeReference<Collection<GrantedAuthority>>() {};

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .publishOn(Schedulers.boundedElastic())
                .mapNotNull(auth -> restTemplate.getForObject("lb://ACCESSCONTROL-SERVICE/ac/jwt/getClaims/"+auth.getCredentials().toString(), Claims.class))
                .log()
                .onErrorResume(e -> Mono.error(new RequestException("bad token", "400-Bad Request")))
                .publishOn(Schedulers.boundedElastic())
                .map(claims -> new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        (List<GrantedAuthority>)restTemplate.getForObject("lb://ACCESSCONTROL-SERVICE/ac/user/getAuthorities/"+claims.getSubject(), List.class)
                ));
    }
}
