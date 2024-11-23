package com.example.ecommerce.Service;

import com.example.ecommerce.DTO.CartDTO;
import com.example.ecommerce.DTO.ProductDTO;
import com.example.ecommerce.DTO.UserDTO;
import com.example.ecommerce.Entity.CartEntity;
import com.example.ecommerce.Entity.ProductEntity;
import com.example.ecommerce.Entity.User;
import com.example.ecommerce.Exception.ResourceNotFoundException;
import com.example.ecommerce.Repository.CartRepo;
import com.example.ecommerce.Repository.ProductRepo;
import com.example.ecommerce.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;


    public CartDTO addProductToCart(Long user_id,Long product_id){
        User user = userRepo.findById(user_id)
                .orElseThrow(()->new ResourceNotFoundException("can not find user with id : "+user_id));
        UserDTO userDTO = modelMapper.map(user,UserDTO.class);

        ProductEntity productEntity = productRepo.findById(product_id)
                .orElseThrow(()->new ResourceNotFoundException("can not find product with id : "+product_id));
        ProductDTO productDTO = modelMapper.map(productEntity,ProductDTO.class);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setProduct(productEntity);
        cartEntity.setUser(user);

        CartEntity save = cartRepo.save(cartEntity);
        return modelMapper.map(save,CartDTO.class);
    }

    public List<CartDTO> getCartByUser(Long user_id) {

        User user = userRepo.findById(user_id).orElseThrow(() -> new RuntimeException("can not find user with id : "+user_id));
        List<CartEntity> cartEntities = cartRepo.findByUser(user);

        return cartEntities.stream()
                .map(cartEntity -> modelMapper.map(cartEntity, CartDTO.class))
                .toList();
    }


}
