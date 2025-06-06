/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.pierandrei.SimulaProva.enuns.DificuldadeDaPerguntaEnum;

import java.util.List;

public record ResponseDto(
        @JsonProperty("quantidade_de_perguntas")
        int quantidade_de_perguntas,

        @JsonProperty("tema_da_pergunta")
        String tema_da_pergunta,

        @JsonProperty("dificuldade_da_pergunta")
        DificuldadeDaPerguntaEnum dificuldadeDaPergunta,


        List<PerguntaDto> perguntas
) {}
