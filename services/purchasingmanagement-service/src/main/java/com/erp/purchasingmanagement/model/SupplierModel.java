package com.erp.purchasingmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "suppliers")
public class SupplierModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Company Name cannot be blank or null")
    @Size(min = 3, max = 100, message = "company name must be between 3 and 100 characters")
    @Column(name = "companyName", length = 100)
    private String companyName;

    @DecimalMin(value = "10", message = "Enter a correct bank account number.")
    @Column(name = "bankAccountNumber")
    private Long bankAccountNumber;

    @NotBlank(message = "")
    @Column(name = "bankAccount")
    private String bankAccount;

    @NotBlank(message = "Address cannot be blank or null")
    @Size(min = 3, max = 200, message = "address must be between 3 and 200 characters")
    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Column(name = "email", length = 80, nullable = false, unique = true)
    private String email;

    @NotNull(message = "Telephone cannot be null")
    @OneToMany
    @JoinColumn(name = "supplierTelephones", nullable = false)
    private List<SupplierTelephone> supplierTelephones;

    @NotNull(message = "Status cannot be null")
    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Status status;
}
