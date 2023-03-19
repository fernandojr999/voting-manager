package br.com.fernando.votingmanager.user;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDto {
    UUID id;
    String name;

    public static User toUser(UserDto userDto){
        return User.builder()
                .name(userDto.name)
                .build();
    }

    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.id)
                .name(user.name)
                .build();
    }
}
