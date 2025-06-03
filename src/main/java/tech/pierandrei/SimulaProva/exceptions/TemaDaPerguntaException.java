package tech.pierandrei.SimulaProva.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "O tema da pergunta não deve passar de 300 caractéres!")
public class TemaDaPerguntaException extends RuntimeException {

    public TemaDaPerguntaException() {
        super("O tema da pergunta não deve passar de 300 caractéres!");
    }

    public TemaDaPerguntaException(String message) {
        super(message);
    }

    public TemaDaPerguntaException(int numero) {
        super("O tema da pergunta não deve passar de 300 caractéres!");
    }
}