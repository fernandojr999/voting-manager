package br.com.fernando.votingmanager.voting;

import br.com.fernando.votingmanager.VoteType;
import br.com.fernando.votingmanager.user.User;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Builder
public class Vote {

    @ManyToOne
    Meeting meeting;

    @ManyToOne
    User user;

    VoteType voteType;


}
