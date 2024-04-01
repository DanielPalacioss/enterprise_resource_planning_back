package com.erp.gateway.client.model.salesmanagement.customer;

import com.erp.gateway.client.model.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerReferenceModel {


    private Long id;

    private String reference;

    private String description;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private Status status;
}
