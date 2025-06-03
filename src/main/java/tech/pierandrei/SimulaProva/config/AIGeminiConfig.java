package tech.pierandrei.SimulaProva.config;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import tech.pierandrei.SimulaProva.ContextoAIGemini;
import tech.pierandrei.SimulaProva.dto.RequestDto;


@Configuration
public class AIGeminiConfig {
    private final ContextoAIGemini contextoAIGemini;

    public AIGeminiConfig(ContextoAIGemini contextoAIGemini) {
        this.contextoAIGemini = contextoAIGemini;
    }

    @Value("${gemini.api.key}")
    private String key;

    public GenerateContentResponse configAIGemini(RequestDto requestDto) throws JsonProcessingException {
        Client client = Client.builder().apiKey(key).build();

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.0-flash",
                        contextoAIGemini.contextAlpha(requestDto),
                        null);

        return response;
    }
}
