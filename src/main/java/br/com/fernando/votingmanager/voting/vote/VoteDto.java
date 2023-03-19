package br.com.fernando.votingmanager.voting.vote;

import br.com.fernando.votingmanager.VoteType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class VoteDto {
    UUID id;
    UUID meetingId;
    UUID userId;
    VoteType voteType;
}
