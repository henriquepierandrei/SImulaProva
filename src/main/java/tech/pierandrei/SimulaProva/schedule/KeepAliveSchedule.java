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

@Component
public class KeepAliveSchedule {
    private static final Logger log = LoggerFactory.getLogger(KeepAliveSchedule.class);
    private final RestTemplate restTemplate = new RestTemplate();

    // Executa a cada 10 minutos.
    @Scheduled(cron = "0 */10 * * * *")
    public void keepAlive() {
        try {
            String url = "https://simulaprova-latest.onrender.com/health";
            restTemplate.getForObject(url, String.class);
            log.debug("Keep-alive ping enviado com sucesso.");
        } catch (Exception e) {
            log.error("Erro no keep-alive: {}",e.getMessage());
        }
    }
}
