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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pierandrei.SimulaProva.config.AIGeminiConfig;
import tech.pierandrei.SimulaProva.dto.RequestDto;
import tech.pierandrei.SimulaProva.dto.ResponseDto;
import tech.pierandrei.SimulaProva.exceptions.NumeroDePerguntasException;
import tech.pierandrei.SimulaProva.exceptions.TemaDaPerguntaException;

@Service
public class AIGeminiService {
    @Autowired
    private AIGeminiConfig aiGeminiConfig;


    public ResponseDto enviarRequisicao(RequestDto requestDto) throws JsonProcessingException {
        if (requestDto.quantidadeDePerguntas() < 1 || requestDto.quantidadeDePerguntas() > 10 ) throw new NumeroDePerguntasException();

        if (requestDto.temaDasPerguntas().length() > 300) throw new TemaDaPerguntaException();

        var response = aiGeminiConfig.configAIGemini(requestDto);


        String jsonLimpo = response.text().replaceAll("(?s)```json(.*?)```", "$1").trim();
        ObjectMapper mapper = new ObjectMapper();

        ResponseDto responseEmDto = mapper.readValue(jsonLimpo, ResponseDto.class);
        return responseEmDto;

    }
}
