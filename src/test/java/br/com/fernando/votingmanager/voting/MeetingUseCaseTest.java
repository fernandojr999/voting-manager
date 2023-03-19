package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.VotingManagerApplication;
import br.com.fernando.votingmanager.shared.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

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

    @Test
    public void start_session_shold_to_valid_session(){
        Meeting meeting = meetingUseCase.create(MeetingDto.builder().description("REUNIAO TESTE 1").build());
        UUID meetingId = meeting.id;

        meeting = meetingUseCase.startSession(MeetingStartSessionDto.builder().meetingId(meetingId).build());
        assertEquals(meeting.endSession, meeting.getStartSession().plusMinutes(1));

        assertThrows(RuntimeException.class, () -> {
            meetingUseCase.startSession(MeetingStartSessionDto.builder().meetingId(meetingId).build());
        });
    }

    @Test
    public void getResult_should_to_valid_Meeting(){
        assertThrows(RuntimeException.class, () -> {
            MeetingResultDto meetingResultDto = meetingUseCase.getResult(UUID.randomUUID());
        });
    }

    @Test
    public void get_result_should_to_return_votes(){
        Meeting meeting = meetingUseCase.create(MeetingDto.builder().description("REUNIAO TESTE 1").build());
        UUID meetingId = meeting.id;

        MeetingResultDto meetingResultDto = meetingUseCase.getResult(meetingId);
        assertEquals(meetingResultDto.meetingId, meetingId);
        assertEquals(meetingResultDto.quantityNO, 0);
        assertEquals(meetingResultDto.quantityYES, 0);
        assertEquals(meetingResultDto.message, "Essa sessão ainda não foi aberta");
    }
}
