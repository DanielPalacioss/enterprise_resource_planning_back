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

    private final GatewayAuthBasicFilter gatewayAuthBasicFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("salesmanagement-service", r -> r.path("/sm/**")
                        .filters(f -> f.filter(gatewayAuthBasicFilter))
                        .uri("lb://SALESMANAGEMENT-SERVICE"))
                .build();
    }
}
