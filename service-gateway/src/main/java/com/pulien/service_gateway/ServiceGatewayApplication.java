package com.pulien.service_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceGatewayApplication {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, PreserveHeadersGatewayFilterFactory preserveHeadersFilterFactory){
		return builder.routes()
				.route("auth-route", r -> r.path("/auth/**").uri("lb://auth-manager"))
				.route("card-route", r -> r.path("/cards/**").uri("lb://card-manager"))
				.route("user-route", r -> r.path("/user/**").uri("lb://user-manager"))
				.route("orch-route", r -> r.path("/orchestrator/**").uri("lb://orchestrator"))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ServiceGatewayApplication.class, args);
	}

}
