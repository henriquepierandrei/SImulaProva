package tech.pierandrei.SimulaProva;

import org.springframework.stereotype.Component;
import tech.pierandrei.SimulaProva.dto.RequestDto;

@Component
public class ContextoAIGemini {

    public String contextAlpha(RequestDto requestDto){
        String prompt = String.format(
                "Crie um quiz sobre %s com %d perguntas. " +
                        "Retorne APENAS um JSON válido no seguinte formato:\n" +
                        "{\n" +
                        "  \"quantidade_de_perguntas\": %d,\n" +
                        "  \"tema_da_pergunta\": \"%s\",\n" +
                        "  \"perguntas\": [\n" +
                        "    {\n" +
                        "      \"pergunta\": \"Sua pergunta aqui?\",\n" +
                        "      \"alternativas\": [\"A) Opção 1\", \"B) Opção 2\", \"C) Opção 3\", \"D) Opção 4\"],\n" +
                        "      \"gabarito\": \"A\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n\n" +
                        "Não adicione texto explicativo, apenas o JSON.",
                requestDto.temaDasPerguntas(), requestDto.quantitdadeDePerguntas(), requestDto.quantitdadeDePerguntas(), requestDto.temaDasPerguntas()
        );

        return prompt;
    }
}
