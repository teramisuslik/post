package com.example.my_blog.Controller;

import com.example.my_blog.Entity.User;
import com.example.my_blog.Repocitory.UserRepository;
import com.example.my_blog.Service.UserService;
import com.example.my_blog.jwt.AuthRequest;
import com.example.my_blog.jwt.AuthResponse;
import com.example.my_blog.jwt.JwtTockenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Tag(name = "main_Controller", description = " главный контроллер")
@Slf4j
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;
    private final JwtTockenUtils jwtTockenUtils;


    @GetMapping("/main")
    @Operation(
            summary = "вывод приветствия на экран",
            description = "Выводит Hello World!"
    )
    @ApiResponse(responseCode = "200", description = "работает")
    @SecurityRequirement(name = "JWT")
    public String get_main(){
        return  "Hello World";
    }

    @PostMapping("/register")
    @Operation(
            summary = "регистрируем пользователя",
            description = "регистрируем пользователя"
    )
    @ApiResponse(responseCode = "200", description = "работает")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest){
        log.info("добавляем пользователя, вызов метода userService.register");
        String massage = userService.register(authRequest);
        return ResponseEntity.ok(massage);
    }

    @PostMapping("/login")
    @Operation(
            summary = "авторизируем пользователя",
            description = "авторизируем пользователя"
    )
    @ApiResponse(responseCode = "200", description = "работает")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        User user = userService.login(authRequest);
        String tocken = jwtTockenUtils.generateTocken(user);
        return ResponseEntity.ok(new AuthResponse(tocken));
    }

}
