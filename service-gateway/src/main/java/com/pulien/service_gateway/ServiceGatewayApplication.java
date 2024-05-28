package com.pulien.service_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceGatewayApplication {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
		return builder.routes().route("auth-route", r -> r.path("/auth/**").uri("lb://auth-manager")).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ServiceGatewayApplication.class, args);
	}

}
