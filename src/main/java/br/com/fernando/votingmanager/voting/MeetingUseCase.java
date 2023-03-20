package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.VoteType;
import br.com.fernando.votingmanager.shared.NotFoundException;
import br.com.fernando.votingmanager.user.User;
import br.com.fernando.votingmanager.user.UserRepository;
import br.com.fernando.votingmanager.voting.vote.Vote;
import br.com.fernando.votingmanager.voting.vote.VoteDto;
import br.com.fernando.votingmanager.voting.vote.VoteRepository;
import br.com.fernando.votingmanager.voting.vote.VoteUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MeetingUseCase {
    MeetingRepository meetingRepository;
    VoteRepository voteRepository;
    VoteUseCase voteUseCase;
    private final UserRepository userRepository;

    public MeetingDto create(MeetingDto meetingDto){
        Meeting meeting =meetingRepository.save(MeetingDto.toMeeting(meetingDto));

        return MeetingDto.builder()
                .description(meeting.description)
                .id(meeting.id)
                .build();
    }

    public List<MeetingDto> getAll(){
        List<Meeting> meetings = meetingRepository.findAll();

        List<MeetingDto> meetingDtos = new ArrayList<>();
        meetings.forEach(meeting -> {
            meetingDtos.add(MeetingDto.builder()
                            .id(meeting.id)
                            .description(meeting.description)
                            .endSession(meeting.endSession)
                            .startSession(meeting.startSession)
                    .build());
        });

        return meetingDtos;
    }

    public Meeting startSession(MeetingStartSessionDto meetingStartSessionDto){
        Meeting meeting = meetingRepository.findById(meetingStartSessionDto.meetingId).orElseThrow(() -> new NotFoundException("Pauta não encontrada"));

        if(meeting.startSession != null){
            throw new RuntimeException("Essa sessão já está aberta");
        }

        LocalDateTime startSession = LocalDateTime.now();

        meeting.setStartSession(startSession);

        if(meetingStartSessionDto.endSession == null){
            meeting.setEndSession(startSession.plusMinutes(1));
        } else {
            if (meetingStartSessionDto.endSession.isBefore(startSession)){
                throw new RuntimeException("O término da sessão não pode ser inferior ao início da mesma");
            }
            meeting.setEndSession(meetingStartSessionDto.endSession);
        }

        return meetingRepository.save(meeting);
    }

    public MeetingResultDto getResult(UUID meetingId){
        Meeting meeting = meetingRepository.findByIdWithVotes(meetingId).orElseThrow(() -> new NotFoundException("Pauta não encontrada"));

        List<Vote> votesYES = meeting.getVotes().stream().filter(vote -> vote.getVoteType().equals(VoteType.YES)).toList();


        List<Vote> votesNO  = meeting.getVotes().stream().filter(vote -> vote.getVoteType().equals(VoteType.NO)).toList();

        String message = "";

        if(meeting.startSession == null){
            message = "Essa sessão ainda não foi aberta";
        } else if(meeting.endSession.isAfter(LocalDateTime.now())){
            message = "Essa sessão está aberta";
        } else if(meeting.endSession.isBefore(LocalDateTime.now())){
            message = "Essa sessão está encerrada";
        }

        return MeetingResultDto.builder()
                .quantityNO(votesNO.size())
                .quantityYES(votesYES.size())
                .meetingId(meetingId)
                .message(message)
                .build();
    }

    public void registerVote(VoteDto voteDto){
        Meeting meeting = meetingRepository.findById(voteDto.getMeetingId()).orElseThrow(() -> new NotFoundException("Pauta não encontrada"));
        User user = userRepository.findById(voteDto.getUserId()).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if(meeting.startSession == null || meeting.endSession.isBefore(LocalDateTime.now())){
            throw new RuntimeException("Essa sessão não está aberta");
        }

        Optional<Vote> vote = voteRepository.findByMeetingIdAndUserId(voteDto.getMeetingId(), voteDto.getUserId());

        if(vote.isEmpty()){
            voteUseCase.create(voteDto);
        } else {
            throw new RuntimeException("Esse cooperado já votou");
        }
    }
}
