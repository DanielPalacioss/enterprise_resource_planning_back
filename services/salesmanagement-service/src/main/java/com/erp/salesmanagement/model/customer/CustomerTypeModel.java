package com.erp.salesmanagement.model.customer;

import com.erp.salesmanagement.model.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customerType")
public class CustomerTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "'category' cannot be blank or null")
    @Size(max = 60, min = 1, message = "'category' must be between 1 and 60 characters")
    @Column(name= "type", length = 60, nullable = false, unique = true)
    private String type;

    @NotBlank(message = "'description' cannot be blank or null")
    @Size(max = 250, min = 10, message = "'description' must be between 10 and 250 characters")
    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private Status status;
}
