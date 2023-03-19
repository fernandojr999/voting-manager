package br.com.fernando.votingmanager.voting;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class MeetingStartSessionDto {
    UUID meetingId;
    LocalDateTime endSession;
}
