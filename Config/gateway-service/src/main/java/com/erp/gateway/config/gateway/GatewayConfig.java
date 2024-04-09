package com.erp.gateway.config.gateway;

import com.erp.gateway.config.gateway.filter.GatewayAuthBasicFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GatewayConfig {

    private GatewayAuthBasicFilter gatewayAuthBasicFilter;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("SALESMANAGEMENT-SERVICE", r -> r.path("/sm/**")
                        .filters(f -> f.filter(gatewayAuthBasicFilter, -1, true))
                        .uri("lb://SALESMANAGEMENT-SERVICE"))
                .route("ACCESSCONTROL-SERVICE", r -> r.path("/ac/**")
                        .filters(f -> f.filter(gatewayAuthBasicFilter, -1, true))
                        .uri("lb://ACCESSCONTROL-SERVICE"))
                .build();
    }
}
