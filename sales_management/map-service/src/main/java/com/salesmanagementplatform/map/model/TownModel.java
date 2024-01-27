package com.salesmanagementplatform.map.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "town")
public class TownModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "town", unique = true)
    private String town;

    @OneToOne
    @JoinColumn(name = "departmentId", nullable = false)
    private DepartmentModel departmentId;
}
