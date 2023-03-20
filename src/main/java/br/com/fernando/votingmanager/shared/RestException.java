package br.com.fernando.votingmanager.shared;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RestException implements Serializable {

    @Serial
    private static final long serialVersionUID = -426018316636277229L;

    private String message;
    private LocalDateTime timestamp;

    public RestException(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}

