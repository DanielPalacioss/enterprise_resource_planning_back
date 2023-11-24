package com.salesmanagementplatform.customer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "status")
public class Status {
    @Id
    private Boolean id;
    @Column(name = "name")
    private String name;
}
