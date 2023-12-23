package com.salesmanagementplatform.invoices.model;

import com.salesmanagementplatform.invoices.error.exceptions.RequestException;
import com.salesmanagementplatform.invoices.model.order.OrderModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "invoice")
public class InvoiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(name = "paid", nullable = false)
    private double paid;

    @Column(name = "exchange", nullable = false)
    private double exchange;

    @Column(name="invoiceDate", nullable = false, updatable = false)
    private LocalDateTime invoiceDate;

    @Column(name="invoiceUpdateDate")
    private LocalDateTime invoiceUpdateDate;

    @Future(message = "The date must be in the future.")
    @Column(name="expirationDate")
    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "invoiceStatus")
    InvoiceStatusModel invoiceStatus;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "paymentMethod")
    InvoicePaymentMethodModel paymentMethod;

    @NotNull
    @OneToOne
    @JoinColumn(name = "orderId")
    private OrderModel order;

    public void addExchange()
    {
        if(paid >= order.getTotal()) setExchange(paid - order.getTotal());
        else throw new RequestException("The payment value must be greater than or equal to the total of the invoice.","400-Bad Request");
    }
}
