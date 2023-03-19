package br.com.fernando.votingmanager.voting;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
