package com.example.my_blog.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "id", example = "1")
    private Long id;

    @Column(nullable = false,unique = true,name = "login")
    @Schema(description = "username", example = "Ivan")
    private String username;

    @Column(nullable = false)
    @Schema(description = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column()
    private Role role;
}
