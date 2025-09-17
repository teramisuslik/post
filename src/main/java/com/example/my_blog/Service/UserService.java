package com.example.my_blog.Service;

import com.example.my_blog.Entity.Role;
import com.example.my_blog.Entity.User;
import com.example.my_blog.Repocitory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public String addUser(User user){
        user.setRole(Role.User);
        log.info("записываем пользователя в бд, вызов метода userRepository.save()");
        userRepository.save(user);
        log.info("пользователь записан успешно");
        return "User added successfully";
    }

}
