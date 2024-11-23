package com.example.ecommerce.Service;

import com.example.ecommerce.DTO.ProductDTO;
import com.example.ecommerce.Entity.ProductEntity;
import com.example.ecommerce.Exception.ResourceNotFoundException;
import com.example.ecommerce.Repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepo productRepo, ModelMapper modelMapper) {
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
    }

    public ProductDTO addProduct(ProductDTO productDTO){
        ProductEntity productEntity = modelMapper.map(productDTO,ProductEntity.class);
        ProductEntity save = productRepo.save(productEntity);
        return modelMapper.map(save,ProductDTO.class);
    }

    public List<ProductDTO> getAllProduct(){
        List<ProductEntity> productEntity = productRepo.findAll();

        return productEntity
                .stream()
                .map(productEntity1 -> modelMapper.map(productEntity1,ProductDTO.class))
                .toList();
    }

    public ProductDTO getProductById(Long id){
        Optional<ProductEntity> productEntity = productRepo.findById(id);
        return modelMapper.map(productEntity,ProductDTO.class);
    }

    public void isExist(Long id){
        boolean exists = productRepo.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Product not found with id: "+id);
    }

    public ProductDTO deleteById(Long id) {
        isExist(id);
        ProductEntity productEntity = productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product with id "+id+" not present."));

        productRepo.delete(productEntity);
        return modelMapper.map(productEntity, ProductDTO.class);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        isExist(id);
        ProductEntity productEntity = modelMapper.map(productDTO,ProductEntity.class);
        productEntity.setId(id);

        ProductEntity save = productRepo.save(productEntity);
        return modelMapper.map(save,ProductDTO.class);
    }
}
