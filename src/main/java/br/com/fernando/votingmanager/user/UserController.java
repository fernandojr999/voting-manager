package br.com.fernando.votingmanager.user;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    UserUseCase userUseCase;

    @GetMapping
    public List<User> getAll(){
        return userUseCase.getAll();
    }

    @PostMapping
    public UserDto save(@RequestBody UserDto userDto){
       return userUseCase.create(userDto);
    }
}
