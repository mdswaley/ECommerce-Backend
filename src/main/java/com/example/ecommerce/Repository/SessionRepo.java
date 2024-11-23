package com.example.ecommerce.Repository;

import com.example.ecommerce.Entity.User;
import com.example.ecommerce.Entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepo extends JpaRepository<Session, Long> {
    List<Session> findByUser(User user);
    Optional<Session> findByRefreshToken(String refreshToken);

    List<Session> findByUserId(Long userId);
}
