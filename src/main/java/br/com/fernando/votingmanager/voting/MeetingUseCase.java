package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.VoteType;
import br.com.fernando.votingmanager.shared.NotFoundException;
import br.com.fernando.votingmanager.voting.vote.Vote;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MeetingUseCase {
    MeetingRepository meetingRepository;

    public Meeting create(MeetingDto meetingDto){
        return meetingRepository.save(MeetingDto.toMeeting(meetingDto));
    }

    public List<Meeting> getAll(){
        return meetingRepository.findAll();
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

        List<Vote> votesYES = meeting.getVotes().stream().filter(vote -> vote.equals(VoteType.YES)).toList();
        List<Vote> votesNO  = meeting.getVotes().stream().filter(vote -> vote.equals(VoteType.NO)).toList();

        String message = "";

        if(meeting.startSession == null){
            message = "Essa sessão ainda não foi aberta";
        } else if(meeting.endSession.isBefore(LocalDateTime.now())){
            message = "Essa sessão está aberta";
        } else if(meeting.endSession.isAfter(LocalDateTime.now())){
            message = "Essa sessão está encerrada";
        }

        return MeetingResultDto.builder()
                .quantityNO(votesNO.size())
                .quantityYES(votesYES.size())
                .meetingId(meetingId)
                .message(message)
                .build();
    }
}
