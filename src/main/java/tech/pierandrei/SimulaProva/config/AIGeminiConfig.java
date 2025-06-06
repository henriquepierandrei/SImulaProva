/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.config;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import tech.pierandrei.SimulaProva.dto.RequestDto;


@Configuration
public class AIGeminiConfig {
    private static final Logger log = LoggerFactory.getLogger(AIGeminiConfig.class);
    private final ContextoAIGemini contextoAIGemini;


    public AIGeminiConfig(ContextoAIGemini contextoAIGemini) {
        this.contextoAIGemini = contextoAIGemini;
    }

    @Value("${gemini.api.key}")
    private String key;

    private final String MODEL = "gemini-2.5-flash-preview-05-20";

    public GenerateContentResponse configAIGemini(RequestDto requestDto) throws JsonProcessingException {
        Client client = Client.builder().apiKey(key).build();


        GenerateContentResponse response =
                client.models.generateContent(
                        MODEL,
                        contextoAIGemini.contextAlpha(requestDto),
                        null);
        log.debug("Chamando a API Gemini AI com o modelo: {}", MODEL);

        return response;
    }
}
