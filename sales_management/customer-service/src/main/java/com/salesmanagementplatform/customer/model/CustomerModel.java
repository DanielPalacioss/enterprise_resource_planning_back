package com.salesmanagementplatform.customer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="customer")
public class CustomerModel {

    @Id
    private Long id;

    @NotEmpty(message="no puede ser vacio")
    @Column(name = "fullName", length = 60, nullable = false)
    private String fullName;

    @Column(name = "lastName", length = 60, nullable = false)
    private String lastName;

    @Column(name = "companyName", length = 100)
    private String companyName;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "address2", length = 200)
    private String address2;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @Column(name = "telephone", nullable = false)
    private Long telephone;

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Column(name = "email", length = 80, nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "customerTypeId", nullable = false)
    private CustomerTypeModel customerType;

    @ManyToOne
    @JoinColumn(name = "customerCategoryId", nullable = false)
    private CustomerCategoryModel customerCategory;

    @NotNull(message="no puede ser vacio")
    @ManyToOne
    @JoinColumn(name = "customerReferenceId", nullable = false)
    private CustomerReferenceModel customerReference;

    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private Status status;

}
