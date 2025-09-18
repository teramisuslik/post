package com.example.my_blog.Service;

import com.example.my_blog.Entity.Post;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private  final PostService postService;
    private final BCryptPasswordEncoder passwordEncoder;

    public String register(AuthRequest authRequest){
        User user = User.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(Role.USER)
                .build();
        log.info("записываем пользователя в бд, вызов метода userRepository.save()");
        userRepository.save(user);
        log.info("пользователь записан успешно");
        return "пользователь зарегистрирован";
    }

    public String registerAuthor(AuthRequest authRequest){
        User user = User.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(Role.AUTHOR)
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

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("нет такого пользователя"));
    }

    public void createPost(Post post, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        post.setAuthor(user);
        postService.addPost(post);
        List<Post> posts = user.getPosts() != null ? user.getPosts() : new ArrayList<>();
        posts.add(post);
        user.setPosts(posts);
    }
}
