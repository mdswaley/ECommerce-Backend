package com.example.ecommerce.Controller;

import com.example.ecommerce.DTO.CartDTO;
import com.example.ecommerce.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    private final CartService cartService;

    @PostMapping("/{userId}/UserAddProduct/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) {
        try {
            cartService.addProductToCart(userId, productId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added to cart successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product to cart: " + e.getMessage());
        }
    }

    @GetMapping(path = "/getCartByUser/{user_id}")
    public ResponseEntity<List<CartDTO>> getCartByUser(@PathVariable Long user_id){
        return ResponseEntity.ok(cartService.getCartByUser(user_id));
    }
}
