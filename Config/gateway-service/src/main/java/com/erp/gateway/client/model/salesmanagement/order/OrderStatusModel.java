package com.erp.gateway.client.model.salesmanagement.order;

import lombok.Data;

@Data
public class OrderStatusModel {

    private Long id;

    private String status;

    private String description;
}
