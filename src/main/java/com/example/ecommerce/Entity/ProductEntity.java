package com.example.ecommerce.Entity;

import com.example.ecommerce.DTO.AvailableTypes;
import com.example.ecommerce.DTO.PolicyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer sku;
    private String category;
    private BigDecimal price;
    private String description;
    private String url;

    @Enumerated(EnumType.STRING)
    private AvailableTypes availability;
    private Integer review;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<PolicyType> policy;


}
