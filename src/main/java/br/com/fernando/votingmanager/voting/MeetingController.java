package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.voting.vote.VoteDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/meeting")
public class MeetingController {
    MeetingUseCase meetingUseCase;

    @GetMapping
    public List<MeetingDto> getAll(){
        return meetingUseCase.getAll();
    }

    @PostMapping
    public MeetingDto save(@RequestBody MeetingDto meetingDto){
        return meetingUseCase.create(meetingDto);
    }

    @PostMapping("/start-session")
    public void startSession(@RequestBody MeetingStartSessionDto meetingStartSessionDto){
        meetingUseCase.startSession(meetingStartSessionDto);
    }

    @PostMapping("/register-vote")
    public void registerVote(@RequestBody VoteDto voteDto){
        meetingUseCase.registerVote(voteDto);
    }

    @GetMapping("/result/{id}")
    public MeetingResultDto getResult(@PathVariable UUID id){
        return meetingUseCase.getResult(id);
    }
}
