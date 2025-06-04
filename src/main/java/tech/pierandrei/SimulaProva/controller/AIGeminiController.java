/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * Manter a aplicação sempre recebendo requisição. (Evitar Cold Start)
     *   @param requestDto  RequestDto para enviar os dados da requisição para a IA
     *   @return 200 ok, com a resposta em DTO
     */
    @PostMapping("/request")
    public ResponseEntity<?> enviarRequest(@RequestBody RequestDto requestDto, HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK.value()).body(aiGeminiService.enviarRequisicao(requestDto, request));
    }



}
