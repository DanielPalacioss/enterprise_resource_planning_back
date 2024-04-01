package com.erp.gateway.config.security;

import com.erp.gateway.config.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class HttpSecurityConfig {
    @Autowired
    private ReactiveAuthenticationManager authenticationManager;  //Inyectar el AuthenticationManager reactivo

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrfConfig -> csrfConfig.disable())
                .httpBasic(httpBasicSpec -> httpBasicSpec.disable())  //Habilitar autenticación HTTP Basic (si es necesario)
                .formLogin(formLoginSpec -> formLoginSpec.disable())
                .authenticationManager(authenticationManager)  //Establecer el AuthenticationManager reactivo
                .addFilterBefore(authenticationFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.GET, "/auth/authenticate", "/auth/public", "/error").permitAll()
                        // .pathMatchers(HttpMethod.GET, "sm/customer/**").hasAuthority("READ_CUSTOMER")
                        // .pathMatchers("sm/**").hasAuthority("ALL_SALESMANAGEMENT")
                        .anyExchange().denyAll()
                )
                .build();
    }
    /*@Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.GET, "/auth/authenticate").permitAll();
                    authConfig.requestMatchers( "/auth/public").permitAll();
                    authConfig.requestMatchers("error").permitAll();
                    //authConfig.requestMatchers(HttpMethod.GET, "sm/customer/**").hasAuthority("READ_CUSTOMER");
                    //authConfig.requestMatchers("sm/**").hasAuthority("ALL_SALESMANAGEMENT");
                    authConfig.anyRequest().denyAll();
                }).build();
    }*/
}


