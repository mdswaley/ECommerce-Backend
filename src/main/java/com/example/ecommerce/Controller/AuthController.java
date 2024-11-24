package com.example.ecommerce.Controller;

import com.example.ecommerce.DTO.*;
import com.example.ecommerce.Entity.CartEntity;
import com.example.ecommerce.Entity.User;
import com.example.ecommerce.Repository.CartRepo;
import com.example.ecommerce.ResponceData.UserProductResponse;
import com.example.ecommerce.Service.AuthService;
import com.example.ecommerce.Service.CartService;
import com.example.ecommerce.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    private final CartRepo cartRepo;

    @Value("${deploy.env}")
    private String devEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody SignUpDto signUpDto){
        UserDTO userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);

    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response){
        LoginResponseDto loginResponseDto = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true); // only we can see not other like attackers
        cookie.setSecure("production".equals(devEnv));
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
        String refresh = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()-> new AuthenticationServiceException("Refresh Token not Found Inside the Cookies."));

        LoginResponseDto loginResponseDto = authService.refreshToken(refresh);

        return ResponseEntity.ok(loginResponseDto);
    }

    @GetMapping("/user-products")
    public ResponseEntity<List<UserProductResponse>> getUserProducts() {
        List<CartEntity> cartEntities = cartRepo.findAll();
        List<UserProductResponse> response = userService.getUserProducts(cartEntities);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-products/{user_id}")
    public ResponseEntity<UserProductResponse> getUserProductsByUser(@Valid @PathVariable Long user_id) {
        UserProductResponse response = userService.getUserProductsByUser(user_id);
        return ResponseEntity.ok(response);
    }




}
