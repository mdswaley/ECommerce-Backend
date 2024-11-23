package com.example.ecommerce.ResponceData;

import com.example.ecommerce.DTO.ProductDTO;
import com.example.ecommerce.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProductResponse {
    private UserDTO user;
    private List<ProductDTO> products;
}
