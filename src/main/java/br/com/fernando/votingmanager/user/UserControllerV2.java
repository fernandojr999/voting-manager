package br.com.fernando.votingmanager.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v2/users")
public class UserControllerV2 {
    // Este é um exemplo de como versionar os endpoints, expecificamos a versão já no path do endpoint
    // Dessa forma versionamos apenas o controller e fazemos as chamadas aos métodos da versão descrita


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
