package com.example.my_blog.Controller;

import com.example.my_blog.Entity.User;
import com.example.my_blog.Repocitory.UserRepository;
import com.example.my_blog.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "main_Controller", description = " главный контроллер")
@Slf4j
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;

    @GetMapping("/main")
    @Operation(
            summary = "вывод приветствия на экран",
            description = "Выводит Hello World!"
    )
    @ApiResponse(responseCode = "200", description = "работает")
    public String get_main(){
        return  "Hello World";
    }

    @PostMapping("/adduser")
    @Operation(
            summary = "добавляем пользователя",
            description = "добавляет пользователя"
    )
    @ApiResponse(responseCode = "200", description = "работает")
    public void addUser(@RequestBody User user){
        log.info("добавляем пользователя, вызов метода userService.addUser");
        String massage = userService.addUser(user);
        log.info(massage);
    }


}
