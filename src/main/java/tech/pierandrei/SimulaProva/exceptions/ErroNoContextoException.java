/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */
package tech.pierandrei.SimulaProva.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção gerada quando houver erro no processamento do contexto para a GEMINI API
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "A escolha do contexto foi inadequada.")
public class ErroNoContextoException extends RuntimeException{

    public ErroNoContextoException() {
        super("A escolha do contexto foi inadequada.");
    }

    public ErroNoContextoException(String message) {
        super(message);
    }

    public ErroNoContextoException(int numero) {
        super("A escolha do contexto foi inadequada.");
    }
}
