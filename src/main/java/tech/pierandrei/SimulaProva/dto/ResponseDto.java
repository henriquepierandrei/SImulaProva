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


        List<Pergunta> perguntas
) {
    public record Pergunta(
            String pergunta,
            List<String> alternativas,
            String gabarito,
            String explicacao
    ) {}




}
