package br.com.fernando.votingmanager.voting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MeetingRepository extends JpaRepository<Meeting, UUID> {

}
