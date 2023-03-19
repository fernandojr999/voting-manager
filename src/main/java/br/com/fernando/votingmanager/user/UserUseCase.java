package br.com.fernando.votingmanager.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserUseCase {

    UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public UserDto create(UserDto userDto){
        return UserDto.toUserDto(userRepository.save(UserDto.toUser(userDto)));
    }

}
