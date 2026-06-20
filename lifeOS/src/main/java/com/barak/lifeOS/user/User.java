package com.barak.lifeOS.user;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name should not exceed 75 characters")
    private String name;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 75, message = "Username should be between 3 and 75 characters")
    private String username;

    @Email(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
    
    private boolean deleted = false;
}
