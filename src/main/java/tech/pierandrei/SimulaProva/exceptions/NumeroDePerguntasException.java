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
