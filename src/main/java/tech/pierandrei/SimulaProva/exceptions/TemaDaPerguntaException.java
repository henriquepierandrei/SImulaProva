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
 * Exceção gerada quando o Tema da pergunta tiver um tamanho inválido.
 */
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