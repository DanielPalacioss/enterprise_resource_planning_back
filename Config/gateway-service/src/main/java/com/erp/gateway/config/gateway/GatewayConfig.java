package com.erp.gateway.config.gateway;

import com.erp.gateway.config.gateway.filter.GatewayAuthBasicFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    GatewayAuthBasicFilter gatewayAuthBasicFilter; // filtro para agregar en el header de cada request el auth basic

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
}
