package com.erp.gateway.client.model.salesmanagement.invoice;

import lombok.Data;

@Data
public class InvoiceStatusModel {

    private Long id;

    private String status;

    private String description;
}
