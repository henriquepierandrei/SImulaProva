/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tech.pierandrei.SimulaProva.dto.alpha.RequestDto;
import tech.pierandrei.SimulaProva.dto.omega.RequestOmegaDto;
import tech.pierandrei.SimulaProva.dto.omega.ResponseOmegaGeracaoDto;

import java.util.Map;

@Component
public class ContextoAIGemini {
    private static final Logger log = LoggerFactory.getLogger(ContextoAIGemini.class);

    /**
     * Contexto para enviar o request para a IA da Gemini. (Contexto utilizado para gerar perguntas objetiva)
     * @param requestDto - Obtém os dados para construir o contexto
     * @return - Retorna o contexto para construir o request
     */
    public String contextAlpha(RequestDto requestDto){
        // Prompt não pode ser alterado, caso alterado ocorrerá erros no response, impossibilitando do mapper executar o mapeamento para o DTO.
        log.debug("Contexto Alpha sendo lido...");
        String prompt = String.format(
                """
                        Você é um especialista em criação de perguntas e resposta e um pesquisador altamente qualificado de questões de provas para estudantes que buscam aprender de verdade. Crie um quiz sobre "%s" com exatamente %d perguntas,
                        lembre-se de criar perguntas do ponto de vista do tema tratado importantes e bem elaboradas.
                                            
                        REQUISITOS:
                        - Evite perguntas repetitivas ou genéricas.
                        - Perguntas de múltipla escolha com 4 alternativas cada
                        - Alternativas bem distribuídas em dificuldade
                        - Uma única resposta correta por pergunta
                        - Perguntas claras, objetivas e bem elaboradas
                        - Evite pegadinhas desnecessárias
                        - Quando a pergunta for difícil é para dificultar de verdade.
                        - Varie o nível de dificuldade (fácil, médio, difícil)
                        - As perguntas deve conter:
                                Pergunta principal: 150-250 caracteres
                                                
                                Mínimo: 100 caracteres (muito básica)
                                Ideal: 100-150 caracteres
                                Máximo: 300 caracteres (sem ficar verbosa)
                                                
                                Alternativas: 50-100 caracteres cada
                                                
                                Mínimo: 50 caracteres
                                Ideal: 80-100 caracteres
                                Máximo: 150 caracteres
                                                
                                Explicação: 200-300 caracteres
                                                
                                Mínimo: 150 caracteres
                                Ideal: 200-250 caracteres
                                            
                                            
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


    /**
     * Contexto parta enviar o request para a IA da gemini. (Contexto utilizado para gerar perguntas discursivas)
     * @param requestDto - Obtém os dados para construir o contexto
     * @return - Retorna o contexto para construir o request
     */
    public String contextOmega(RequestDto requestDto){
        // Prompt não pode ser alterado, caso alterado ocorrerá erros no response, impossibilitando do mapper executar o mapeamento para o DTO.
        log.debug("Contexto Omega sendo lido...");
        String prompt = String.format(
                """
                        Você é um especialista em criação de perguntas discursivas e um pesquisador altamente qualificado de questões de provas para estudantes que buscam aprender de verdade. Crie um quiz sobre "%s" com exatamente %d perguntas discursivas.
                        
                        NÍVEL DE DIFICULDADE: %s
                        
                        DIRETRIZES POR NÍVEL:
                        - FACIL: Perguntas conceituais básicas, definições, características principais do tema
                        - MEDIO: Perguntas que exigem análise, comparação, relação entre conceitos
                        - DIFICIL: Perguntas complexas que exigem síntese, avaliação crítica e conhecimento aprofundado
                                            
                        REQUISITOS GERAIS:
                        - Evite perguntas repetitivas ou genéricas
                        - Perguntas discursivas apropriadas ao nível de dificuldade solicitado
                        - Questões claras, objetivas e bem elaboradas
                        - Perguntas que permitam respostas desenvolvidas e argumentadas
                        - Adapte a complexidade conforme o nível: %s
                        
                        CARACTERÍSTICAS DAS PERGUNTAS:
                        - Extensão: 80 a 400 caracteres
                        - Linguagem clara e direta
                        - Focadas no tema: %s
                        - Apropriadas para o nível de dificuldade especificado
                                            
                        FORMATO DE RESPOSTA OBRIGATÓRIO:
                        Retorne APENAS um JSON válido, sem texto adicional, comentários, explicações ou formatação markdown.
                                            
                        {
                          "quantidade_de_perguntas": %d,
                          "tema_da_pergunta": "%s",
                          "dificuldade_da_pergunta": "%s",
                          "perguntas": {
                            1: "Primeira pergunta discursiva",
                            2: "Segunda pergunta discursiva",
                            3: "Terceira pergunta discursiva"
                          }
                        }
                        
                        CRÍTICO:
                        - Use exatamente o valor "%s" no campo dificuldade_da_pergunta
                        - Numere as perguntas sequencialmente (1, 2, 3...)
                        - Retorne SOMENTE o JSON, sem qualquer texto adicional
                        - Garanta que o JSON seja válido e parseável
                        """,
                requestDto.temaDasPerguntas(),
                requestDto.quantidadeDePerguntas(),
                requestDto.dificuldadeDaPergunta().toString(),
                requestDto.dificuldadeDaPergunta().toString(),
                requestDto.temaDasPerguntas(),
                requestDto.quantidadeDePerguntas(),
                requestDto.temaDasPerguntas(),
                requestDto.dificuldadeDaPergunta().toString(),
                requestDto.dificuldadeDaPergunta().toString()
        );
        return prompt;
    }


    /**
     * Contexto parta enviar as respostas para a IA da gemini avaliar.
     * @param requestOmegaDto - Obtém os dados para construir o contexto
     * @return -  Retorna o contexto para construir o request
     */
    public String contextOmegaParaCorrigirResposta(RequestOmegaDto requestOmegaDto){
        ResponseOmegaGeracaoDto questoes = requestOmegaDto.responseOmegaGeracaoDto();
        Map<Long, String> respostasDoAluno = requestOmegaDto.respostas();

        StringBuilder promptBuilder = new StringBuilder();

        promptBuilder.append(String.format(
                """
                Você é um professor especialista em "%s" responsável por corrigir %d perguntas discursivas de nível %s.
                
                CRITÉRIOS DE CORREÇÃO:
                - Nota de 0 a 10 para cada pergunta (NUNCA exceder 10)
                - FACIL: Foco em conceitos básicos e definições corretas
                - MEDIO: Análise, comparação e desenvolvimento argumentativo
                - DIFICIL: Conhecimento aprofundado, síntese e pensamento crítico
                
                ESCALA DE NOTAS:
                - 0-2: Resposta incorreta ou muito incompleta
                - 3-4: Resposta parcialmente correta, conceitos básicos presentes
                - 5-6: Resposta adequada, conceitos corretos mas desenvolvimento limitado
                - 7-8: Boa resposta, conceitos corretos e bem desenvolvidos
                - 9-10: Excelente resposta, completa e demonstra domínio do assunto
                
                EXPLICAÇÃO:
                - Forneça uma explicação detalhada da resposta ideal como um professor especialista
                - Aponte pontos positivos e negativos da resposta do aluno
                - Dê sugestões construtivas para melhoria
                - Mantenha tom educativo e encorajador
                
                PERGUNTAS E RESPOSTAS:
                """,
                questoes.tema_da_pergunta(),
                questoes.quantidade_de_perguntas(),
                questoes.dificuldadeDaPergunta().toString()
        ));

        // Adicionar cada pergunta e resposta
        for (Map.Entry<Long, String> pergunta : questoes.perguntas().entrySet()) {
            Long id = pergunta.getKey();
            String textoPergunta = pergunta.getValue();
            String respostaAluno = respostasDoAluno.getOrDefault(id, "Não respondida");

            promptBuilder.append(String.format(
                    """
                    
                    PERGUNTA %d: %s
                    RESPOSTA DO ALUNO: %s
                    """,
                    id, textoPergunta, respostaAluno
            ));
        }

        promptBuilder.append(String.format(
                """
                
                FORMATO DE RESPOSTA:
                Retorne apenas este JSON válido:
                
                {
                  "quantidade_de_perguntas": %d,
                  "tema_da_pergunta": "%s",
                  "dificuldade_da_pergunta": "%s",
                  "nota": {
                    1: 8,
                    2: 6
                  },
                  "explicacao": {
                    1: "RESPOSTA MODELO: [explicação da resposta ideal]. AVALIAÇÃO: [análise da resposta do aluno]. SUGESTÕES: [dicas para melhoria].",
                    2: "RESPOSTA MODELO: [explicação da resposta ideal]. AVALIAÇÃO: [análise da resposta do aluno]. SUGESTÕES: [dicas para melhoria]."
                  }
                }
                
                DIRETRIZES FINAIS:
                - NUNCA atribua nota superior a 10
                - Explicação de 200-400 caracteres por pergunta
                - Use os IDs corretos das perguntas: %s
                - Seja justo, construtivo e educativo
                - Estruture explicação: RESPOSTA MODELO + AVALIAÇÃO + SUGESTÕES, porém sem citá-las.
                """,
                questoes.quantidade_de_perguntas(),
                questoes.tema_da_pergunta(),
                questoes.dificuldadeDaPergunta().toString(),
                String.join(", ", questoes.perguntas().keySet().stream().map(String::valueOf).toList())
        ));

        return promptBuilder.toString();
    }


}
