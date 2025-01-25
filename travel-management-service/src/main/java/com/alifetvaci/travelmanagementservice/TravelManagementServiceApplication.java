package com.alifetvaci.travelmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TravelManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelManagementServiceApplication.class, args);
	}

}
