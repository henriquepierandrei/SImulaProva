/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pierandrei.SimulaProva.config.AIGeminiConfig;
import tech.pierandrei.SimulaProva.dto.alpha.RequestDto;
import tech.pierandrei.SimulaProva.dto.alpha.ResponseAlphaDto;
import tech.pierandrei.SimulaProva.dto.omega.RequestOmegaDto;
import tech.pierandrei.SimulaProva.dto.omega.ResponseOmegaFinalDto;
import tech.pierandrei.SimulaProva.dto.omega.ResponseOmegaGeracaoDto;
import tech.pierandrei.SimulaProva.exceptions.NumeroDePerguntasException;
import tech.pierandrei.SimulaProva.exceptions.TemaDaPerguntaException;

@Service
public class AIGeminiService {
    private static final Logger log = LoggerFactory.getLogger(AIGeminiService.class);
    @Autowired
    private AIGeminiConfig aiGeminiConfig;

    /**
     * Validar o Request
     * @param requestDto - Objeto recebido para fazer validação
     */
    private void validacaoDoRequest(RequestDto requestDto){
        if (requestDto.quantidadeDePerguntas() < 1 || requestDto.quantidadeDePerguntas() > 10 ) throw new NumeroDePerguntasException();
        if (requestDto.temaDasPerguntas().length() > 300) throw new TemaDaPerguntaException();
    }

    /**
     * Envia a requisição para gerar as perguntas e respostas (Questões objetivas)
     * @param requestDto - Request para construir a resposta da GEMINI API
     * @param httpServletRequest - Obter as informações do client
     * @return - Retorna o response gerado pela GEMINI API
     * @throws JsonProcessingException - Caso ocorra erro na manipulação do Json
     */
    public ResponseAlphaDto enviarRequisicaoContextoAlpha(RequestDto requestDto, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        validacaoDoRequest(requestDto);
        var response = aiGeminiConfig.configAIGemini(requestDto, "alpha");

        // Lapidando o json para evitar caractéres que geram problema no response
        var jsonLimpo = response.text().replaceAll("(?s)```json(.*?)```", "$1").trim();

        log.debug("Mapper: Json -> DTO.");

        // Mapper para mapear o response de json para DTO
        ObjectMapper mapper = new ObjectMapper();
        ResponseAlphaDto responseEmDto = mapper.readValue(jsonLimpo, ResponseAlphaDto.class);

        log.debug("{} | User-Agent: {}", requestDto.temaDasPerguntas(), httpServletRequest.getHeader("user-agent"));
        return responseEmDto;
    }

    /**
     * Envia a requisição para gerar as perguntas (Questões discursivas)
     * @param requestDto - Request para construir a resposta da GEMINI API
     * @param httpServletRequest - Obter as informações do client
     * @return - Retorna o response gerado pela GEMINI API
     * @throws JsonProcessingException - Caso ocorra erro na manipulação do Json
     */
    public ResponseOmegaGeracaoDto enviarRequisicaoContextoOmega(RequestDto requestDto, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        validacaoDoRequest(requestDto);

        var response = aiGeminiConfig.configAIGemini(requestDto, "omega");

        // Lapidando o json para evitar caractéres que geram problema no response
        var jsonLimpo = response.text().replaceAll("(?s)```json(.*?)```", "$1").trim();

        log.debug("Mapper: Json -> DTO.");

        // Mapper para mapear o response de json para DTO
        ObjectMapper mapper = new ObjectMapper();
        ResponseOmegaGeracaoDto responseEmDto = mapper.readValue(jsonLimpo, ResponseOmegaGeracaoDto.class);

        log.debug("{} | User-Agent: {}", requestDto.temaDasPerguntas(), httpServletRequest.getHeader("user-agent"));
        return responseEmDto;
    }

    /**
     * Envia a requisição para a GEMINI API corrigir as respostas (Questões discursivas)
     * @param requestOmegaDto - Request para construir a resposta da GEMINI API
     * @param httpServletRequest - Obter as informações do client
     * @return - Retorna o response gerado pela GEMINI API
     * @throws JsonProcessingException - Caso ocorra erro na manipulação do Json
     */
    public ResponseOmegaFinalDto enviarRequisicaoContextoFinalOmega(RequestOmegaDto requestOmegaDto, HttpServletRequest httpServletRequest) throws JsonProcessingException{
        var response = aiGeminiConfig.configAIGeminiFinal(requestOmegaDto);
        // Lapidando o json para evitar caractéres que geram problema no response
        var jsonLimpo = response.text().replaceAll("(?s)```json(.*?)```", "$1").trim();

        log.debug("Mapper: Json -> DTO.");

        // Mapper para mapear o response de json para DTO
        ObjectMapper mapper = new ObjectMapper();
        ResponseOmegaFinalDto responseEmDto = mapper.readValue(jsonLimpo, ResponseOmegaFinalDto.class);

        return responseEmDto;
    }

}
