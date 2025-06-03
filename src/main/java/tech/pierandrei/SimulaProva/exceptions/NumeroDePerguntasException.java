package tech.pierandrei.SimulaProva.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Número de perguntas inválido")
public class NumeroDePerguntasException extends RuntimeException {

    public NumeroDePerguntasException() {
        super("Número de perguntas deve estar entre 1 e 10");
    }

    public NumeroDePerguntasException(String message) {
        super(message);
    }

    public NumeroDePerguntasException(int numero) {
        super("Número de perguntas inválido: " + numero + ". Deve estar entre 1 e 10");
    }
}
