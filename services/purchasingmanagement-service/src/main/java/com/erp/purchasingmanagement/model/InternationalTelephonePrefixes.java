package com.erp.purchasingmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "internationalTelephonePrefixes")
public class InternationalTelephonePrefixes {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long Id;

    @NotBlank(message = "")
    @Column(name = "prefix", length = 10)
    private String prefix;

    @NotBlank(message = "")
    @Column(name = "country", length = 25)
    private String country;
}
