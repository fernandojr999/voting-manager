package br.com.fernando.votingmanager.voting;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class MeetingResultDto {
    UUID meetingId;
    int quantityYES;
    int quantityNO;
    String message;
}
