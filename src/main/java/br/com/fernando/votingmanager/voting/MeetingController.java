package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.voting.vote.VoteDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/meeting")
public class MeetingController {
    MeetingUseCase meetingUseCase;

    @GetMapping
    public List<Meeting> getAll(){
        return meetingUseCase.getAll();
    }

    @PostMapping
    public void save(@RequestBody MeetingDto meetingDto){
        meetingUseCase.create(meetingDto);
    }

    @PostMapping("/start-session")
    public void startSession(@RequestBody MeetingStartSessionDto meetingStartSessionDto){
        meetingUseCase.startSession(meetingStartSessionDto);
    }

    @PostMapping("/register-vote")
    public void registerVote(@RequestBody VoteDto voteDto){
        meetingUseCase.registerVote(voteDto);
    }
}
