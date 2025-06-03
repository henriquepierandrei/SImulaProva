package tech.pierandrei.SimulaProva.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record ErrorResponse(
        String codigo,
        String mensagem,
        int status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp
) {
    public ErrorResponse(String codigo, String mensagem, int status) {
        this(codigo, mensagem, status, LocalDateTime.now());
    }
}
