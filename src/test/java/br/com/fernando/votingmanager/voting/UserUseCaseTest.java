package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.VotingManagerApplication;
import br.com.fernando.votingmanager.shared.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = VotingManagerApplication.class)
public class UserUseCaseTest extends BaseTest {

    @Test
    public void teste(){
        System.out.println("");
    }
}
