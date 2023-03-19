package br.com.fernando.votingmanager.voting.vote;

import br.com.fernando.votingmanager.VoteType;
import br.com.fernando.votingmanager.user.User;
import br.com.fernando.votingmanager.voting.Meeting;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_vote")
public class Vote {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    Meeting meeting;

    @ManyToOne
    User user;

    VoteType voteType;
}
