package br.edu.ifpr.workshop_llm.helper;

import java.time.LocalDateTime;

public record Message(String author, String message, LocalDateTime sendAt) {
    public String toString() {
        return String.format("Autor: %s\nEnviado em: %tF %<tT\n\n%s", author, sendAt, message);
    }
}
