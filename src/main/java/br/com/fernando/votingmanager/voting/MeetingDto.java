package br.com.fernando.votingmanager.voting;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class MeetingDto {
    UUID id;

    String description;

    public static Meeting toMeeting(MeetingDto meetingDto){
        return Meeting.builder()
                .description(meetingDto.description)
                .build();
    }
}
