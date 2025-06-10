/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 *
 * @param codigo - Código do erro
 * @param mensagem - Mensagem do erro
 * @param status - Status do erro
 * @param timestamp - Momento do ero
 */
public record ErrorResponse(
        String codigo,
        String mensagem,
        int status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp
) {
    public ErrorResponse(String codigo, String mensagem, int status) {
        this(codigo, mensagem, status, LocalDateTime.now());
    }
}
