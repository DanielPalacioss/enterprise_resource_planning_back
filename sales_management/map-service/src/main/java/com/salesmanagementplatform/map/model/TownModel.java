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

    @Column(name = "town", length = 50)
    private String town;

    @ManyToOne
    @JoinColumn(name = "department", nullable = false)
    private DepartmentModel department;
}
