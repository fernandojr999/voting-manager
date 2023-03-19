package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.voting.vote.Vote;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_meeting")
public class Meeting{
    @Id
    @GeneratedValue
    UUID id;

    String description;

    LocalDateTime startSession;
    LocalDateTime endSession;

    @OneToMany(mappedBy = "meeting")
    private List<Vote> votes;
}
