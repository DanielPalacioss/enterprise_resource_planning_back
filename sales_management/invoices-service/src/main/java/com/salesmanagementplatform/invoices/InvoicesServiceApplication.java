package com.salesmanagementplatform.invoices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class InvoicesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoicesServiceApplication.class, args);
	}

}
