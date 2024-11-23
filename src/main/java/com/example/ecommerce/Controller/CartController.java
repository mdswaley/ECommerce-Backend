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
public class CartController {
    private final CartService cartService;

    @PostMapping(path = "{user_id}/UserAddProduct/{pro_id}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long user_id, @PathVariable Long pro_id){
        cartService.addProductToCart(user_id, pro_id);
        return new ResponseEntity<>("Product added to cart successfully!", HttpStatus.CREATED);
    }

    @GetMapping(path = "/getCartByUser/{user_id}")
    public ResponseEntity<List<CartDTO>> getCartByUser(@PathVariable Long user_id){
        return ResponseEntity.ok(cartService.getCartByUser(user_id));
    }
}
