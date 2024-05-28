package org.pulien.cardmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroServiceCard {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceCard.class, args);
	}

}

