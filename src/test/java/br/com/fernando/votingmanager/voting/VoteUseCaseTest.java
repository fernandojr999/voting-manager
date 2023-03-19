package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.VoteType;
import br.com.fernando.votingmanager.VotingManagerApplication;
import br.com.fernando.votingmanager.shared.BaseTest;
import br.com.fernando.votingmanager.shared.NotFoundException;
import br.com.fernando.votingmanager.user.User;
import br.com.fernando.votingmanager.user.UserRepository;
import br.com.fernando.votingmanager.voting.vote.Vote;
import br.com.fernando.votingmanager.voting.vote.VoteDto;
import br.com.fernando.votingmanager.voting.vote.VoteUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = VotingManagerApplication.class)
public class VoteUseCaseTest extends BaseTest {

    @Autowired
    VoteUseCase voteUseCase;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Test
    public void create_vote_should_to_valid_if_exist_user(){
        VoteDto dto = VoteDto.builder()
                .userId(UUID.randomUUID())
                .build();

        assertThrows(NotFoundException.class, () -> voteUseCase.create(dto));
    }

    @Test
    public void create_vote_should_to_valid_if_exist_meeting(){
        VoteDto dto = VoteDto.builder()
                .userId(userRepository.save(User.builder().build()).getId())
                .meetingId(UUID.randomUUID())
                .build();

        assertThrows(NotFoundException.class, () -> voteUseCase.create(dto));
    }

    @Test
    public void create_vote_should_work_and_should_to_return_All_votes(){
        VoteDto dto = VoteDto.builder()
                .userId(userRepository.save(User.builder().build()).getId())
                .meetingId(meetingRepository.save(Meeting.builder().build()).getId())
                .voteType(VoteType.NO)
                .build();

        assertNotNull(voteUseCase.create(dto).getId());

        List<Vote> votes = voteUseCase.getAll();
        assertEquals(votes.size(), 1);
    }
}
