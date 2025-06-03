package tech.pierandrei.SimulaProva.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NumeroDePerguntasException.class)
    public ResponseEntity<ErrorResponse> handleNumeroDePerguntasException(NumeroDePerguntasException ex) {
        ErrorResponse error = new ErrorResponse(
                "NUMERO_PERGUNTAS_INVALIDO",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(TemaDaPerguntaException.class)
    public ResponseEntity<ErrorResponse> handleTemaDaPerguntaException(TemaDaPerguntaException ex) {
        ErrorResponse error = new ErrorResponse(
                "TEMA_DA_PERGUNTA_INVALIDO",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleEnumInvalido(HttpMessageNotReadableException ex) {
        String mensagem = ex.getMessage();

        // Verificar se é erro de enum
        if (mensagem.contains("DificuldadeDaPerguntaEnum")) {
            // Extrair o valor inválido
            String valorInvalido = extrairValorInvalido(mensagem);

            ErrorResponse error = new ErrorResponse(
                    "DIFICULDADE_INVALIDA",
                    "Dificuldade inválida: '" + valorInvalido + "'. Valores aceitos: MUITO_FACIL, FACIL, MEDIO, DIFICIL, MUITO_DIFICIL",
                    HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // Outros erros de JSON
        ErrorResponse error = new ErrorResponse(
                "JSON_INVALIDO",
                "Formato JSON inválido",
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    private String extrairValorInvalido(String mensagem) {
        try {
            // Regex para extrair o valor entre aspas após "inválida:"
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("inválida: ([A-Z_]+)");
            java.util.regex.Matcher matcher = pattern.matcher(mensagem);
            if (matcher.find()) {
                return matcher.group(1);
            }
            return "valor desconhecido";
        } catch (Exception e) {
            return "valor desconhecido";
        }
    }
}