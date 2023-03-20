package br.com.fernando.votingmanager.voting;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class MeetingDto {
    UUID id;

    String description;

    LocalDateTime startSession;
    LocalDateTime endSession;

    public static Meeting toMeeting(MeetingDto meetingDto){
        return Meeting.builder()
                .description(meetingDto.description)
                .build();
    }
}
