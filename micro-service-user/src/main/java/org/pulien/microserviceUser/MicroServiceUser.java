package org.pulien.microserviceUser;

import org.pulien.microserviceUser.authentification.JwtFilter;
import org.pulien.microserviceUser.authentification.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroServiceUser {

	public static void main(String[] args) {
		SpringApplication.run(CardManagerApplication.class, args);
	}

}

