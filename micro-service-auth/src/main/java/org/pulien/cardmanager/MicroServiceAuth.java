package org.pulien.cardmanager;

import org.pulien.cardmanager.authentification.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication

public class MicroServiceAuth {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceAuth.class, args);
	}

	@Bean
	public JwtUtil jwtUtil(){
		return new JwtUtil();
	}
}