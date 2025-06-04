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
import com.google.auth.oauth2.GoogleCredentials;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pierandrei.SimulaProva.config.AIGeminiConfig;
import tech.pierandrei.SimulaProva.dto.RequestDto;
import tech.pierandrei.SimulaProva.dto.ResponseDto;
import tech.pierandrei.SimulaProva.exceptions.NumeroDePerguntasException;
import tech.pierandrei.SimulaProva.exceptions.TemaDaPerguntaException;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class AIGeminiService {
    private static final Logger log = LoggerFactory.getLogger(AIGeminiService.class);
    @Autowired
    private AIGeminiConfig aiGeminiConfig;


    public ResponseDto enviarRequisicao(RequestDto requestDto, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        if (requestDto.quantidadeDePerguntas() < 1 || requestDto.quantidadeDePerguntas() > 10 ) throw new NumeroDePerguntasException();
        if (requestDto.temaDasPerguntas().length() > 300) throw new TemaDaPerguntaException();


        var response = aiGeminiConfig.configAIGemini(requestDto);

        // Lapidando o json para evitar caractéres que geram problema no response
        var jsonLimpo = response.text().replaceAll("(?s)```json(.*?)```", "$1").trim();

        log.debug("Mapeando o Json para retornar em DTO.");
        // Mapper para mapear o response de json para DTO
        ObjectMapper mapper = new ObjectMapper();
        ResponseDto responseEmDto = mapper.readValue(jsonLimpo, ResponseDto.class);


        log.debug("{} | User-Agent: {}", requestDto.temaDasPerguntas(), httpServletRequest.getHeader("user-agent"));
        return responseEmDto;

    }




}
