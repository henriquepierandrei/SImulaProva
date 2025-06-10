/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.dto.omega;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.pierandrei.SimulaProva.enuns.DificuldadeDaPerguntaEnum;

import java.util.Map;

/**
 *
 * @param quantidade_de_perguntas - Quantidade de perguntas extraidas do request
 * @param tema_da_pergunta - Tema das perguntas extraida do request
 * @param dificuldadeDaPergunta - Dificuldade das perguntas extraidas do request
 * @param nota - Nota recebida da GEMINI API após corrigir as perguntas.
 * @param explicacao - Explicação recebida da GEMINI API após corrigir as perguntas.
 */
public record ResponseOmegaFinalDto(
        @JsonProperty("quantidade_de_perguntas")
        int quantidade_de_perguntas,

        @JsonProperty("tema_da_pergunta")
        String tema_da_pergunta,

        @JsonProperty("dificuldade_da_pergunta")
        DificuldadeDaPerguntaEnum dificuldadeDaPergunta,

        @JsonProperty("nota")
        Map<Long, Integer> nota,

        @JsonProperty("explicacao")
        Map<Long, String> explicacao
) {}
