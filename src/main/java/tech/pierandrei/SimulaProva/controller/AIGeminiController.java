package tech.pierandrei.SimulaProva.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.pierandrei.SimulaProva.config.AIGeminiConfig;
import tech.pierandrei.SimulaProva.dto.RequestDto;
import tech.pierandrei.SimulaProva.service.AIGeminiService;

@RestController
@RequestMapping("/ai/generation")
public class AIGeminiController {
    @Autowired
    private AIGeminiService aiGeminiService;


    @PostMapping("/request")
    public ResponseEntity<?> enviarRequest(@RequestBody RequestDto requestDto) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK.value()).body(aiGeminiService.enviarRequisicao(requestDto));
    }

}
