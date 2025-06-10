/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.dto.alpha;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.pierandrei.SimulaProva.enuns.DificuldadeDaPerguntaEnum;

import java.util.List;

/**
 * @param quantidade_de_perguntas - Quantidade de respostas obtidas do request
 * @param tema_da_pergunta -  Tema das perguntas obtida do request
 * @param dificuldadeDaPergunta - Dificuldade das perguntas obtida do request
 * @param perguntas - Lista de perguntas retornada da GEMINI API
 */
public record ResponseAlphaDto(
        @JsonProperty("quantidade_de_perguntas")
        int quantidade_de_perguntas,

        @JsonProperty("tema_da_pergunta")
        String tema_da_pergunta,

        @JsonProperty("dificuldade_da_pergunta")
        DificuldadeDaPerguntaEnum dificuldadeDaPergunta,


        List<PerguntaDto> perguntas

) {
}
