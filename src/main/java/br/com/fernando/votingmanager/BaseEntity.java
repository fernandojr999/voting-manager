package br.com.fernando.votingmanager;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public abstract class BaseEntity {
    UUID id;
}
