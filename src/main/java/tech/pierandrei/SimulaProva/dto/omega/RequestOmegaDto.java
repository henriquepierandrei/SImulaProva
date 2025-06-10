/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.dto.omega;

import java.util.Map;

/**
 * @param responseOmegaGeracaoDto - Response usado para construir o request para a GEMINI API corrigir as perguntas
 * @param respostas - Respostas retornada pelo usuário (ID, Resposta)
 */
public record RequestOmegaDto(
        ResponseOmegaGeracaoDto responseOmegaGeracaoDto,
        Map<Long, String> respostas
) {
}
