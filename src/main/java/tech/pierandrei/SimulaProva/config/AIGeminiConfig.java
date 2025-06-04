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

    public GenerateContentResponse configAIGemini(RequestDto requestDto) throws JsonProcessingException {
        Client client = Client.builder().apiKey(key).build();

        log.debug("Chamando a API Gemini AI com o modelo: gemini-2.0-flash");
        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.0-flash",
                        contextoAIGemini.contextAlpha(requestDto),
                        null);

        return response;
    }
}
