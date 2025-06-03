package tech.pierandrei.SimulaProva.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pierandrei.SimulaProva.config.AIGeminiConfig;
import tech.pierandrei.SimulaProva.dto.RequestDto;
import tech.pierandrei.SimulaProva.dto.ResponseDto;

@Service
public class AIGeminiService {
    @Autowired
    private AIGeminiConfig aiGeminiConfig;


    public ResponseDto enviarRequisicao(RequestDto requestDto) throws JsonProcessingException {
        var response = aiGeminiConfig.configAIGemini(requestDto);


        String jsonLimpo = response.text().replaceAll("(?s)```json(.*?)```", "$1").trim();
        ObjectMapper mapper = new ObjectMapper();

        ResponseDto responseEmDto = mapper.readValue(jsonLimpo, ResponseDto.class);
        return responseEmDto;

    }
}
