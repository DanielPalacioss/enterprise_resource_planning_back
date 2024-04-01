package com.erp.gateway.client.model.salesmanagement.order;

import lombok.Data;

@Data
public class OrderDetails {

    private int productNumber;

    private String productReference;

    private float unitPrice;

    private int units;

    private float discount;

    private float productVat;

    private Double subtotal;

    private Double total;
}
