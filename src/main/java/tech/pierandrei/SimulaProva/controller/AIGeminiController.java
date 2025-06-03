package tech.pierandrei.SimulaProva.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.pierandrei.SimulaProva.dto.RequestDto;
import tech.pierandrei.SimulaProva.service.AIGeminiService;

@CrossOrigin
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
