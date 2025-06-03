package tech.pierandrei.SimulaProva.dto;

import java.util.List;

public record ResponseDto(
        int quantidade_de_perguntas,
        String tema_da_pergunta,
        List<Pergunta> perguntas
) {
    public record Pergunta(
            String pergunta,
            List<String> alternativas,
            String gabarito
    ) {}
}
