package com.example.my_blog.Service;

import com.example.my_blog.Entity.Role;
import com.example.my_blog.Entity.User;
import com.example.my_blog.Repocitory.UserRepository;
import com.example.my_blog.jwt.AuthRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public String register(AuthRequest authRequest){
        User user = User.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(Role.User)
                .build();
        log.info("записываем пользователя в бд, вызов метода userRepository.save()");
        userRepository.save(user);
        log.info("пользователь записан успешно");
        return "пользователь зарегистрирован";
    }

    public User login(AuthRequest authRequest){
        return userRepository.findByUsername(authRequest.getUsername())
                .filter(e -> passwordEncoder.matches(authRequest.getPassword(), e.getPassword()))
                .orElseThrow(() -> new RuntimeException("что- то пошло не так при авторизации"));


    }

}
