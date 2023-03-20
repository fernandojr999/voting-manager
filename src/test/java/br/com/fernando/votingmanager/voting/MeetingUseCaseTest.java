package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.VoteType;
import br.com.fernando.votingmanager.VotingManagerApplication;
import br.com.fernando.votingmanager.shared.BaseTest;
import br.com.fernando.votingmanager.user.UserDto;
import br.com.fernando.votingmanager.user.UserUseCase;
import br.com.fernando.votingmanager.voting.vote.VoteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = VotingManagerApplication.class)
public class MeetingUseCaseTest extends BaseTest{

    @Autowired
    UserUseCase userUseCase;

    @Autowired
    MeetingUseCase meetingUseCase;

    @Test
    public void create_Meeting_should_work(){
        MeetingDto meeting = meetingUseCase.create(MeetingDto.builder().description("REUNIAO TESTE 1").build());
        assertNotNull(meeting.id);

        List<MeetingDto> meetings = meetingUseCase.getAll();
        assertEquals(meetings.size(), 1);
        assertEquals(meetings.get(0).description, "REUNIAO TESTE 1");
    }

    @Test
    public void start_session_shold_to_valid_session(){
        MeetingDto meetingDto = meetingUseCase.create(MeetingDto.builder().description("REUNIAO TESTE 1").build());
        UUID meetingId = meetingDto.id;

        Meeting meeting = meetingUseCase.startSession(MeetingStartSessionDto.builder().meetingId(meetingId).build());
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
        MeetingDto meeting = meetingUseCase.create(MeetingDto.builder().description("REUNIAO TESTE 1").build());
        UUID meetingId = meeting.id;

        MeetingResultDto meetingResultDto = meetingUseCase.getResult(meetingId);
        assertEquals(meetingResultDto.meetingId, meetingId);
        assertEquals(meetingResultDto.quantityNO, 0);
        assertEquals(meetingResultDto.quantityYES, 0);
        assertEquals(meetingResultDto.message, "Essa sessão ainda não foi aberta");
    }

    @Test
    public void registerVote_should_to_valid_session(){
        UserDto user = userUseCase.create(UserDto.builder().name("Teste").build());
        MeetingDto meeting = meetingUseCase.create(MeetingDto.builder().description("REUNIAO TESTE 1").build());
        UUID meetingId = meeting.id;

        assertThrows(RuntimeException.class, () -> {
            meetingUseCase.registerVote(VoteDto.builder().userId(user.getId()).meetingId(meetingId).voteType(VoteType.YES).build());
        });
    }

    @Test
    public void registerVote_shouldnt_allow_duplicate_vote(){
        UserDto user = userUseCase.create(UserDto.builder().name("Teste").build());
        MeetingDto meeting = meetingUseCase.create(MeetingDto.builder().description("REUNIAO TESTE 1").build());
        UUID meetingId = meeting.id;

        meetingUseCase.startSession(MeetingStartSessionDto.builder().meetingId(meetingId).build());

        meetingUseCase.registerVote(VoteDto.builder().userId(user.getId()).meetingId(meetingId).voteType(VoteType.YES).build());

        assertThrows(RuntimeException.class, () -> {
            meetingUseCase.registerVote(VoteDto.builder().userId(user.getId()).meetingId(meetingId).voteType(VoteType.YES).build());
        });
    }
}
