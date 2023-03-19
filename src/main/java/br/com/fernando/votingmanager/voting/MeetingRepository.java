package br.com.fernando.votingmanager.voting;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MeetingRepository extends JpaRepository<Meeting, UUID> {

    @Query("""
            SELECT m, v 
            FROM Meeting m
            LEFT JOIN FETCH Vote v
            WHERE m.id = :meetingId
            """)
    @EntityGraph(attributePaths = {"votes"})
    Optional<Meeting> findByIdWithVotes(UUID meetingId);
}
