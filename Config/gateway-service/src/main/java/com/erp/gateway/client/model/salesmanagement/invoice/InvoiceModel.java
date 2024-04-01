package com.erp.gateway.client.model.salesmanagement.invoice;

import com.erp.gateway.client.model.salesmanagement.order.OrderModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvoiceModel {

    Long id;

    private double paid;

    private double exchange;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private LocalDateTime expirationDate;

    private InvoiceStatusModel invoiceStatus;

    private InvoicePaymentMethodModel paymentMethod;

    private OrderModel order;

}
