package br.com.fernando.votingmanager.voting.vote;

import br.com.fernando.votingmanager.shared.NotFoundException;
import br.com.fernando.votingmanager.user.User;
import br.com.fernando.votingmanager.user.UserRepository;
import br.com.fernando.votingmanager.voting.Meeting;
import br.com.fernando.votingmanager.voting.MeetingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VoteUseCase {
    VoteRepository voteRepository;
    UserRepository userRepository;
    MeetingRepository meetingRepository;

    public Vote create(VoteDto dto){
        User user = userRepository.findById(dto.userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        Meeting meeting = meetingRepository.findById(dto.meetingId).orElseThrow(() -> new NotFoundException("Pauta não encontrada."));

        Vote vote = Vote.builder()
                .user(user)
                .meeting(meeting)
                .voteType(dto.voteType)
                .build();
        return voteRepository.save(vote);
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }
}
