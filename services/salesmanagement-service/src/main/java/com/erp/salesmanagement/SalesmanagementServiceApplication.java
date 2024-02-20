package com.erp.salesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SalesmanagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesmanagementServiceApplication.class, args);
	}

}
