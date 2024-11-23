package com.example.ecommerce.Controller;

import com.example.ecommerce.DTO.ProductDTO;
import com.example.ecommerce.Service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(path = "/addProduct")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.addProduct(productDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.updateProduct(id,productDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ProductDTO> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(productService.deleteById(id));
    }
}
