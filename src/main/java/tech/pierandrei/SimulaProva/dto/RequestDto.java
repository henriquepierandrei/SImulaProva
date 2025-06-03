package tech.pierandrei.SimulaProva.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.pierandrei.SimulaProva.enuns.DificuldadeDaPerguntaEnum;

public record RequestDto(
        @JsonProperty("dificuldade_da_pergunta")
        DificuldadeDaPerguntaEnum dificuldadeDaPergunta,

        @JsonProperty("quantidade_de_perguntas")
        int quantidadeDePerguntas,

        @JsonProperty("tema_das_perguntas")
        String temaDasPerguntas
) {
    public RequestDto {
        // Exemplo de formatação do tema das perguntas
        if (temaDasPerguntas != null && !temaDasPerguntas.startsWith("O tema das perguntas:")) {
            temaDasPerguntas = "O tema das perguntas: " + temaDasPerguntas;
        }
    }
}
