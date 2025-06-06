/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.dto;

import java.util.List;

public record PerguntaDto(
        String pergunta,
        List<String> alternativas,
        String gabarito,
        String explicacao
) {
}
