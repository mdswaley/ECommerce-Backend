package com.example.ecommerce.DTO;

import com.example.ecommerce.Entity.ProductEntity;
import com.example.ecommerce.Entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
public class CartDTO {
    private Long id;
    private User user;
    private ProductEntity product;
}
