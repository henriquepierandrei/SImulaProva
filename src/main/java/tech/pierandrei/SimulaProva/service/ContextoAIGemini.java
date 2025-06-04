/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tech.pierandrei.SimulaProva.dto.RequestDto;

@Component
public class ContextoAIGemini {
    private static final Logger log = LoggerFactory.getLogger(ContextoAIGemini.class);

    // Contexto para enviar o request para a IA da Gemini.
    public String contextAlpha(RequestDto requestDto){
        // Prompt não pode ser alterado, caso alterado ocorrerá erros no response, impossibilitando do mapper executar o mapeamento para o DTO.
        log.debug("Contexto Alpha sendo lido");
        String prompt = String.format(
                """
                Você é um especialista em criação de perguntas e resposta para estudantes de todos os tipos. Crie um quiz sobre "%s" com exatamente %d perguntas, lembre-se de criar perguntas do ponto de vista do tema tratado importantes e essenciais.
                
                REQUISITOS:
                - Perguntas de múltipla escolha com 4 alternativas cada
                - Alternativas bem distribuídas em dificuldade
                - Uma única resposta correta por pergunta
                - Perguntas claras, objetivas e bem elaboradas
                - Evite pegadinhas desnecessárias
                - Varie o nível de dificuldade (muito fácil, fácil, médio, difícil, muito difícil)
                - As perguntas deve conter:
                        Pergunta principal: 80-150 caracteres
                                        
                        Mínimo: 80 caracteres (muito básica)
                        Ideal: 100-130 caracteres
                        Máximo: 200 caracteres (sem ficar verbosa)
                                        
                        Alternativas: 30-60 caracteres cada
                                        
                        Mínimo: 30 caracteres
                        Ideal: 40-50 caracteres
                        Máximo: 80 caracteres
                                        
                        Explicação: 100-200 caracteres
                                        
                        Mínimo: 70 caracteres
                        Ideal: 130-180 caracteres
                
                
                FORMATO DE RESPOSTA:
                Retorne APENAS um JSON válido, sem texto adicional, comentários ou explicações.
                
                Estrutura JSON obrigatória:
                {
                  "quantidade_de_perguntas": %d,
                  "tema_da_pergunta": "%s",
                  "dificuldade_da_pergunta": "%s",
                  "perguntas": [
                    {
                      "pergunta": "Texto da pergunta aqui?",
                      "alternativas": [
                        "A) Primeira opção",
                        "B) Segunda opção", 
                        "C) Terceira opção",
                        "D) Quarta opção"
                      ],
                      "gabarito": "A",
                      "explicacao": "Breve explicação da resposta correta"
                    }
                  ]
                }
                ATENÇÃO: O campo "dificuldade_da_pergunta" DEVE ser exatamente "%s" - não deixe como null!
                                        
                IMPORTANTE: Responda apenas com o JSON válido, sem markdown, sem texto antes ou depois.
                """,
                requestDto.temaDasPerguntas(),
                requestDto.quantidadeDePerguntas(),
                requestDto.quantidadeDePerguntas(),
                requestDto.temaDasPerguntas(),
                requestDto.dificuldadeDaPergunta().toString(),
                requestDto.dificuldadeDaPergunta().toString()
        );
        return prompt;
    }
}
