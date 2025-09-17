package com.example.my_blog.jwt;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
