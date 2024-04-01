package com.erp.gateway.client.model.salesmanagement.invoice;

import com.erp.gateway.client.model.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvoicePaymentMethodModel {

    private Long id;

    private String paymentMethod;

    private String description;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private Status status;
}
