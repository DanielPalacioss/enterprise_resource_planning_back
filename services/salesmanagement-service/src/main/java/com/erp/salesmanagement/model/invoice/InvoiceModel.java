package com.erp.salesmanagement.model.invoice;

import com.erp.salesmanagement.error.exceptions.RequestException;
import com.erp.salesmanagement.model.order.OrderModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
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

    @Column(name="creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name="updateDate")
    private LocalDateTime updateDate;

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
