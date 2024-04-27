package com.erp.salesmanagement.model.order;

import com.erp.salesmanagement.model.customer.CustomerModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orderS")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 200, message = "Shipping address must be between 3 and 200 characters")
    @Column(name = "shippingAddress", length = 200, nullable = false)
    private String shippingAddress;

    @Column(name="creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Future(message = "The date must be in the future.")
    @Column(name="expirationDate")
    private LocalDateTime expirationDate;

    @Column(name="updateDate")
    private LocalDateTime updateDate;

    @DecimalMin(value = "1", message = "The minimum subTotal is 1")
    @Column(name = "subTotal", nullable = false)
    private Double subTotal;

    @DecimalMin(value = "1", message = "The minimum total is 1")
    @Column(name = "total", nullable = false)
    private Double total;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "orderStatus", nullable = false)
    private OrderStatusModel orderStatus;

    @OneToMany(targetEntity = OrderDetails.class, mappedBy = "orderS")
    private List<OrderDetails> orderDetails;
    public void setOrderDetailsList() {
        for(OrderDetails orderDetail : getOrderDetails())
        {
            if (getSubTotal() == null && getTotal() == null) {
                setSubTotal(orderDetail.getSubtotal());
                setTotal(orderDetail.getTotal());
            } else {
                setSubTotal(getSubTotal() + orderDetail.getSubtotal());
                setTotal(getTotal() + orderDetail.getTotal());
            }
        }
    }
}
