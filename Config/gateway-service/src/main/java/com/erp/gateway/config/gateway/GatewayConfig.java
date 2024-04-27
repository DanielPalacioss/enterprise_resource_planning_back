package com.erp.gateway.config.gateway;

import com.erp.gateway.config.gateway.filter.GatewayAuthBasicFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Configuration
public class GatewayConfig {

    @Autowired
    GatewayAuthBasicFilter gatewayAuthBasicFilter; // filtro para agregar en el header de cada request el auth basic

    @Value("${service.security.user.name}")
    private String username;

    @Value("${service.security.user.password}")
    private String password;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("SALESMANAGEMENT-SERVICE", r -> r.path("/sm/**")
                        .filters(f -> f.filter(gatewayAuthBasicFilter))
                        .uri("lb://SALESMANAGEMENT-SERVICE"))
                .route("ACCESSCONTROL-SERVICE", r -> r.path("/ac/**")
                        .filters(f -> f.filter(gatewayAuthBasicFilter))
                        .uri("lb://ACCESSCONTROL-SERVICE"))
                .build();
    }

    @Bean
    public WebClient webClient() { //conexion con nuestro servicio de access control
        String encodedCredentials = Base64.getEncoder().encodeToString((this.username + ":" + this.password).getBytes());
        return WebClient.builder()
                .baseUrl("http://localhost:8095/ac/")
                .defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth(encodedCredentials))
                .build();
    }
}
