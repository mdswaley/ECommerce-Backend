package com.example.ecommerce.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpDto {
    private Long id;

    @NotNull(message = "Should pass name.")
    @NotEmpty(message = "should greater then Zero")
    @NotBlank(message = "Should pass the String")
    @Size(min = 3 , max = 10, message = "name should be in the range of [3,11]")
    private String name;

    private String userName;

    @Email(message = "email should be in valid format")
    @NotNull(message = "Should pass email.")
    @NotBlank(message = "Should pass the String")
    private String email;

    @Column(nullable = false)
    @Pattern(
            regexp = "^(?=.*\\d{2,})(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{6,}$",
            message = "Password must be at least 6 characters long, contain at least 2 digits, and 1 special character."
    )
    private String password;
}
