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
import tech.pierandrei.SimulaProva.dto.alpha.RequestDto;
import tech.pierandrei.SimulaProva.dto.omega.RequestOmegaDto;
import tech.pierandrei.SimulaProva.service.AIGeminiService;

@CrossOrigin
@RestController
@RequestMapping("/ai/generation")
public class AIGeminiController {
    @Autowired
    private AIGeminiService aiGeminiService;

    /**
     * Enviar a requisição utilizando o contexto Alpha (Perguntas Objetivas)
     *   @param requestDto  RequestOmegaDto para enviar os dados da requisição para a IA
     *   @return 200 ok, com a resposta em DTO
     */
    @PostMapping("/alpha")
    public ResponseEntity<?> enviarRequestAlpha(@RequestBody RequestDto requestDto, HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK.value()).body(aiGeminiService.enviarRequisicaoContextoAlpha(requestDto, request));
    }

    /**
     * Enviar a requisição utilizando o contexto Omega (Perguntas Discursivas)
     *   @param requestDto  RequestOmegaDto para enviar os dados da requisição para a IA
     *   @return 200 ok, com a resposta em DTO
     * @throws JsonProcessingException - Erro na manipulação do Json.
     */
    @PostMapping("/omega")
    public ResponseEntity<?> enviarRequestOmega(@RequestBody RequestDto requestDto, HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK.value()).body(aiGeminiService.enviarRequisicaoContextoOmega(requestDto, request));
    }

    /**
     * Enviar a requisição com as resposta para a GEMINI API corrigir e retornar o response. (Perguntas Discursivas)
     * @param requestOmegaDto - Perguntas e respostas para a Gemini API corrigir
     * @param request - Obter os dados do client em que está fazendo a requisição
     * @return 200 ok, com a resposta em DTO
     * @throws JsonProcessingException - Erro na manipulação do Json.
     */
    @PostMapping("/omega/discursive")
    public ResponseEntity<?> enviarRequestRespostaOmega(@RequestBody RequestOmegaDto requestOmegaDto, HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK.value()).body(aiGeminiService.enviarRequisicaoContextoFinalOmega(requestOmegaDto, request));
    }
}
