package com.erp.gateway.config.security.jwt;

import com.erp.gateway.error.Error;
import com.erp.gateway.error.exceptions.RequestException;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final WebClient webClient;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        ParameterizedTypeReference<List<Authority>> typeRef = new ParameterizedTypeReference<>() {};
        return Mono.just(authentication)
                .publishOn(Schedulers.boundedElastic())
                .map(auth -> webClient.get()
                        .uri("jwt/getClaims/"+auth.getCredentials().toString())
                        .retrieve()
                        .bodyToMono(Claims.class)
                        .onErrorResume(e -> {
                            if (e instanceof WebClientResponseException) {
                                WebClientResponseException webClientException = (WebClientResponseException) e;
                                Error errorMessage = webClientException.getResponseBodyAs(Error.class);
                                throw new RequestException(errorMessage.getMessage(), errorMessage.getCode());
                            } else {
                                return Mono.error(e); // Manejar otros errores no relacionados
                            }}))
                .onErrorResume(e -> Mono.error(new RequestException("bad token", "400-Bad Request")))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(claimsMono ->
                        claimsMono.flatMap( claims ->
                        webClient.get()
                                .uri("jwt/getAuthorities/" + claims.sub())
                                .retrieve()
                                .bodyToMono(typeRef)
                                .flatMap(authorities ->
                                        Mono.just(new UsernamePasswordAuthenticationToken(
                                                claims.sub(),
                                                null,
                                                authorities.stream()
                                                        .map(Authority::authority)
                                                        .map(SimpleGrantedAuthority::new)
                                                        .collect(Collectors.toList()))))));
    }
    private record Authority(String authority){}
    private record Claims(String role,String name,String sub,String iat,String exp){}

}
