package br.com.fernando.votingmanager.shared;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Entidade não encontrada");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
