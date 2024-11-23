package com.example.ecommerce.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductDTO {
    private Long id;

    @NotNull(message = "Should pass name.")
    @NotEmpty(message = "should greater then Zero")
    @NotBlank(message = "Should pass the String")
    @Size(min = 3 , max = 10, message = "name should be in the range of [3,11]")
    private String name;

    @Min(value = 1,message = "sku should be greater then 1")
    @NotNull
    private Integer sku;
    private String category;
    private BigDecimal price;
    private String description;

    private String url;

    private AvailableTypes availability;

    @Min(value = 1,message = "review should be greater then or equals to 1")
    @Max(value = 5,message = "review should be less then or equals to 5")
    private Integer review;

    private Set<PolicyType> policy;
}
