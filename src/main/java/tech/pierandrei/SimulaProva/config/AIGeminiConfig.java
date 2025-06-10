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
import tech.pierandrei.SimulaProva.dto.alpha.RequestDto;
import tech.pierandrei.SimulaProva.dto.omega.RequestOmegaDto;


@Configuration
public class AIGeminiConfig {
    private static final Logger log = LoggerFactory.getLogger(AIGeminiConfig.class);
    private final ContextoAIGemini contextoAIGemini;

    public AIGeminiConfig(ContextoAIGemini contextoAIGemini) {
        this.contextoAIGemini = contextoAIGemini;
    }

    /**
     * KEY DA GEMINI API
     */
    @Value("${gemini.api.key}")
    private String key;


    /* Melhor modelo FREE atualmente (10-06-2025) */
    private final String MODEL = "gemini-2.5-flash-preview-05-20";

    /**
     *
     * @param requestDto - Faz a requisição para gerar as perguntas com alternativas
     * @param contexto  - Qual o contexto será utilizado pela IA da Gemini API
     * @return - - Retorna o response da API da Gemini AI.
     * @throws JsonProcessingException - Caso aconteça algum erro na manipulação do Json.
     */
    public GenerateContentResponse configAIGemini(RequestDto requestDto, String contexto) throws JsonProcessingException {
        Client client = Client.builder().apiKey(key).build();
        // Contexto a ser utilizado.
        var prompt = "";

        if(contexto.equals("alpha")){
            prompt = contextoAIGemini.contextAlpha(requestDto);
        } else if (contexto.equals("omega")) prompt = contextoAIGemini.contextOmega(requestDto);

        GenerateContentResponse response =
                client.models.generateContent(
                        MODEL,
                        prompt,
                        null);

        log.debug("API Gemini AI | MODEL: {}", MODEL);
        return response;
    }


    /**
     *
     * @param requestDto - Faz a requisição com o response das perguntas e as respostas.
     * @return - Retorna o response da API da Gemini API.
     * @throws JsonProcessingException - Caso aconteça algum erro na manipulação do Json.
     */
    public GenerateContentResponse configAIGeminiFinal(RequestOmegaDto requestDto) throws JsonProcessingException {
        Client client = Client.builder().apiKey(key).build();

        // Contexto a ser utilizado.
        String prompt = contextoAIGemini.contextOmegaParaCorrigirResposta(requestDto);

        GenerateContentResponse response =
                client.models.generateContent(
                        MODEL,
                        prompt,
                        null);
        log.debug("API Gemini AI | MODEL: {}", MODEL);
        return response;
    }
}
