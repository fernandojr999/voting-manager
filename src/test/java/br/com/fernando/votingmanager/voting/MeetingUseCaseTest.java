package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.VotingManagerApplication;
import br.com.fernando.votingmanager.shared.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = VotingManagerApplication.class)
public class MeetingUseCaseTest extends BaseTest{

    @Autowired
    MeetingUseCase meetingUseCase;

    @Test
    public void create_Meeting_should_work(){
        Meeting meeting = meetingUseCase.create(MeetingDto.builder().description("REUNIAO TESTE 1").build());
        assertNotNull(meeting.id);

        List<Meeting> meetings = meetingUseCase.getAll();
        assertEquals(meetings.size(), 1);
        assertEquals(meetings.get(0).description, "REUNIAO TESTE 1");
    }
}
