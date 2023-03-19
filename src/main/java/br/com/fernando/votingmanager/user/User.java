package br.com.fernando.votingmanager.user;

import br.com.fernando.votingmanager.BaseEntity;
import lombok.Builder;

@Builder
public class User extends BaseEntity {
    String name;
}
