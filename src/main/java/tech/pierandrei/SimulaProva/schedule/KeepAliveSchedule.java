/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
public class KeepAliveSchedule {
    private static final Logger log = LoggerFactory.getLogger(KeepAliveSchedule.class);
    private final RestTemplate restTemplate = new RestTemplate();

    private final String URL = "https://simulaprova-latest.onrender.com/health";

    /**
     * Fazer requisicação a cada 10 minutos para manter a API sempre ativa.
     */
    // Executa a cada 15 minutos.
    @Scheduled(cron = "0 */15 * * * *")
    public void keepAlive() {
        try {
            restTemplate.getForObject(URL, String.class);
            log.debug("Health: {}", LocalDateTime.now());
        } catch (Exception e) {
            log.error("Erro no keep-alive: {}",e.getMessage());
        }
    }
}
