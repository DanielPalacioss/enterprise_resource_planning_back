package com.salesmanagementplatform.orders.client;

import com.salesmanagementplatform.orders.model.customer.CustomerModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service",url = "http://localhost:8081/api/")
public interface CustomerClient {
    @GetMapping(value = "customer/{customerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    CustomerModel getCustomerById(@PathVariable Long customerId);
}
