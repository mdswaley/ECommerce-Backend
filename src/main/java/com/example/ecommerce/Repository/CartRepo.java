package com.example.ecommerce.Repository;

import com.example.ecommerce.Entity.CartEntity;
import com.example.ecommerce.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<CartEntity,Long> {
    List<CartEntity> findByUser(User user);
}
