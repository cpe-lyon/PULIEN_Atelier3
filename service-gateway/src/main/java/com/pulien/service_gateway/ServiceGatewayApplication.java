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

		return builder.routes()
				.route("auth-manager",r -> r.path("/auth-manager/**")
				.filters(f -> f.stripPrefix(1))
				.uri("lb://AUTH-MANAGER"))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ServiceGatewayApplication.class, args);
	}

}
