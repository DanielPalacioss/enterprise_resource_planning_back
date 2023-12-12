package com.salesmanagementplatform.orders.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Status {
    @Id
    private Boolean id;
    @Column(name = "name",unique = true)
    private String name;
}
