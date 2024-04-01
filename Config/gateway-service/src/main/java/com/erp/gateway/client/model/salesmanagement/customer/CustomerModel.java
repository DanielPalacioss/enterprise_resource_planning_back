package com.erp.gateway.client.model.salesmanagement.customer;

import com.erp.gateway.client.model.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerModel {

    private Long id;

    private String fullName;

    private String lastName;

    private String companyName;

    private String address;

    private String address2;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private Long telephone;

    private Long telephone2;

    private String email;

    private CustomerTypeModel customerType;

    private CustomerCategoryModel customerCategory;

    private CustomerReferenceModel customerReference;

    private Status status;

}
