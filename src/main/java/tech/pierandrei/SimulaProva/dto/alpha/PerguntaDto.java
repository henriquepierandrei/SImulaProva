/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.dto.alpha;

import java.util.List;

/**
 *
 * @param pergunta - Pergunta feita pela GEMINI API
 * @param alternativas - Resposta construída pela GEMINI API
 * @param gabarito - Gabarito construída pela GEMINI API
 * @param explicacao - Explicação construída pela GEMINI API
 */
public record PerguntaDto(
        String pergunta,
        List<String> alternativas,
        String gabarito,
        String explicacao
) {
}
