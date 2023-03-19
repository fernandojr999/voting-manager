package br.com.fernando.votingmanager.user;

import br.com.fernando.votingmanager.VotingManagerApplication;
import br.com.fernando.votingmanager.shared.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = VotingManagerApplication.class)
public class UserUseCaseTest extends BaseTest {

    @Autowired
    UserUseCase userUseCase;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create_user_should_work_and_should_to_return_all_users(){
        UserDto userDto = UserDto.builder().name("FERNANDO").build();
        userUseCase.create(userDto);
        assertNotNull(userDto.id);

        List<User> users = userUseCase.getAll();
        assertEquals(users.size(), 1);
        assertEquals(users.get(0).name, "FERNANDO");
    }
}
