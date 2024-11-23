package com.example.ecommerce.Service;

import com.example.ecommerce.DTO.ProductDTO;
import com.example.ecommerce.DTO.SignUpDto;
import com.example.ecommerce.DTO.UserDTO;
import com.example.ecommerce.Entity.CartEntity;
import com.example.ecommerce.Entity.User;
import com.example.ecommerce.Exception.ResourceNotFoundException;
import com.example.ecommerce.Repository.CartRepo;
import com.example.ecommerce.Repository.UserRepo;
import com.example.ecommerce.ResponceData.UserProductResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CartRepo cartRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User with email "+username+" is not present."));
    }


    public User getUserById(Long userId){
        return userRepo.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User with id "+userId+" is not present."));
    }


    public UserDTO signUp(SignUpDto signUpDto) {
        Optional<User> user = userRepo.findByEmail(signUpDto.getEmail());
        if (user.isPresent()){
            throw new BadCredentialsException("User with email "+signUpDto.getEmail()+" already exists");
        }

        User createUser = modelMapper.map(signUpDto,User.class);
        createUser.setPassword(passwordEncoder.encode(createUser.getPassword()));

        User save = userRepo.save(createUser);
        return modelMapper.map(save, UserDTO.class);
    }


    public List<UserProductResponse> getUserProducts(List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .collect(Collectors.groupingBy(CartEntity::getUser)) // Group by user
                .entrySet()
                .stream()
                .map(entry -> {
                    User user = entry.getKey();
                    List<ProductDTO> products = entry.getValue().stream()
                            .map(cart -> modelMapper.map(cart.getProduct(), ProductDTO.class))
                            .collect(Collectors.toList());

                    return new UserProductResponse(
                            new UserDTO(user.getId(), user.getName(), user.getEmail()),
                            products
                    );
                })
                .collect(Collectors.toList());
    }

    public UserProductResponse getUserProductsByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ProductDTO> products = cartRepo.findByUser(user).stream()
                .map(cart -> modelMapper.map(cart.getProduct(), ProductDTO.class))
                .collect(Collectors.toList());

        return new UserProductResponse(
                new UserDTO(user.getId(), user.getName(), user.getEmail()),
                products
        );
    }

}
