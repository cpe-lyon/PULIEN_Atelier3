package org.pulien.microserviceUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.GenericFilterBean;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroServiceUser {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceUser.class, args);
	}

}

