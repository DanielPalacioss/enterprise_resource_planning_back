package com.erp.salesmanagement.model.product;

import com.erp.salesmanagement.model.order.OrderDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class ProductModel {

    @Min(value = 1, message = "The minimum product number is 1")
    @NotNull(message = "Product Number cannot be null")
    @Id
    private int productNumber;

    @Size(min = 10, max = 255, message = "Product Reference must be between 10 and 255 characters")
    @NotBlank(message = "Product Reference cannot be blank or null")
    @Column(name = "productReference", nullable = false, unique = true)
    private String productReference;

    @NotNull
    @DecimalMin(value = "0", message = "The minimum product vat is 0")
    @Column(name = "productVat", columnDefinition = "FLOAT DEFAULT 0")
    private float productVat;

    @DecimalMin(value = "1", message = "The minimum cost price is 1")
    @NotNull(message = "Cost Price cannot be null")
    @Column(name = "costPrice", nullable = false)
    private float costPrice;

    @DecimalMin(value = "1", message = "The minimum sale price is 1")
    @NotNull(message = "salePrice cannot be null")
    @Column(name = "salePrice", nullable = false)
    private float salePrice;

    @Column(name = "earnings", nullable = false)
    private float earnings;

    @NotNull
    @DecimalMax(value = "100", message = "The discount must be between 1% and 100%")
    @DecimalMin(value = "0", message = "The discount must be between 1% and 100%")
    @Column(name = "discount", columnDefinition = "FLOAT DEFAULT 0")
    private float discount;

    @Column(name = "priceUpdateDate")
    private LocalDateTime priceUpdateDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "productCategory", nullable = false)
    private ProductCategoryModel productCategoryModel;

    @ManyToOne
    @JoinColumn(name = "productStatus", nullable = false)
    private ProductStatusModel productStatus;

    @Transient
    private int quantity;
    public void earnings()
    {
        if(getDiscount() > 0)
        {
            float earnings = ((getSalePrice()*getDiscount())/100)-getCostPrice();
            setEarnings(earnings);
        }
        else {
            setEarnings(getSalePrice()-getCostPrice());
        }
    }
    public List<OrderDetails> productsListToOrderDetailsList(List<ProductModel> productList)
    {
        List<OrderDetails> orderDetailsList1 = new ArrayList<OrderDetails>();
        for(ProductModel products: productList) {
            OrderDetails orderDetails = new OrderDetails();
            Double subtotalWithVat, subtotalWithDiscount;
            orderDetails.setProductNumber(products.getProductNumber());
            orderDetails.setDiscount(products.getDiscount());
            orderDetails.setUnitPrice(products.getSalePrice());
            orderDetails.setUnits(products.getQuantity());
            orderDetails.setProductVat(products.getProductVat());
            orderDetails.setProductReference(products.getProductReference());
            orderDetails.setSubtotal((double) (products.getSalePrice() * products.getQuantity()));
            subtotalWithVat = orderDetails.getSubtotal() + (orderDetails.getSubtotal() * products.getProductVat()) / 100;
            subtotalWithDiscount = orderDetails.getSubtotal() - ((orderDetails.getSubtotal() * products.getDiscount()) / 100);
            if (products.getDiscount() > 0) {
                if (products.getProductVat() > 0)
                    orderDetails.setTotal(subtotalWithDiscount + ((subtotalWithDiscount * products.getProductVat()) / 100));

                else orderDetails.setTotal(subtotalWithDiscount);
            } else if (products.getProductVat() > 0) orderDetails.setTotal(subtotalWithVat);
            else orderDetails.setTotal(orderDetails.getSubtotal());
            orderDetailsList1.add(orderDetails);
        }
        return orderDetailsList1;
    }
}