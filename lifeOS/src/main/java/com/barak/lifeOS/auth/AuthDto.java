package com.barak.lifeOS.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AuthDto {

    @Data
    public static class RegisterDto{
        private String firstName;
        private String LastName;
        private String username;
        private String email;
        private String password;
    }

    @Data
    public static class LoginDto{
        private String username;
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class Response{
        private String token;
        private String username;
    }
}