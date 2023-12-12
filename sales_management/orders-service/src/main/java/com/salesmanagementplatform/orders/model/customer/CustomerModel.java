package com.salesmanagementplatform.orders.model.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="customer")
public class CustomerModel {

    @NotNull(message = "Client id cannot be null")
    @Min(value = 1, message = "the client id must be greater than or equal to 1")
    @Id
    private Long id;

    @NotBlank(message = "full name cannot be blank or null")
    @Size(min = 3, max = 60, message = "'full name' must be between 3 and 60 characters")
    @Column(name = "fullName", length = 60, nullable = false)
    private String fullName;

    @NotBlank(message = "last name cannot be blank or null")
    @Size(min = 3, max = 60, message = "'lastName' must be between 3 and 60 characters")
    @Column(name = "lastName", length = 60, nullable = false)
    private String lastName;

    @NotBlank(message = "company name cannot be blank or null")
    @Size(min = 3, max = 100, message = "company name must be between 3 and 100 characters")
    @Column(name = "companyName", length = 100)
    private String companyName;

    @NotBlank(message = "address cannot be blank or null")
    @Size(min = 3, max = 200, message = "address must be between 3 and 200 characters")
    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Size(min = 3, max = 200, message = "address must be between 3 and 200 characters")
    @Column(name = "address2", length = 200)
    private String address2;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @NotNull(message = "Telephone cannot be null")
    @Min(value = 6, message = "the telephone must be greater than or equal to 6")
    @Column(name = "telephone", nullable = false)
    private Long telephone;

    @Min(value = 6, message = "the telephone2 must be greater than or equal to 6")
    @Column(name = "telephone2")
    private Long telephone2;

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Column(name = "email", length = 80, nullable = false, unique = true)
    private String email;

    @NotNull(message = "customerTypeId cannot be null")
    @ManyToOne
    @JoinColumn(name = "customerTypeId", nullable = false)
    private CustomerTypeModel customerType;

    @NotNull(message = "customerCategoryId cannot be null")
    @ManyToOne
    @JoinColumn(name = "customerCategoryId", nullable = false)
    private CustomerCategoryModel customerCategory;

    @NotNull(message = "customerReferenceId cannot be null")
    @ManyToOne
    @JoinColumn(name = "customerReferenceId", nullable = false)
    private CustomerReferenceModel customerReference;

    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private Status status;

}
