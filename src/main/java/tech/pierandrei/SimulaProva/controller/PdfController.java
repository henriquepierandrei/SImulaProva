/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.pierandrei.SimulaProva.dto.alpha.ResponseAlphaDto;
import tech.pierandrei.SimulaProva.service.PdfService;

@RestController
@RequestMapping("/pdf")
public class PdfController {
    @Autowired
    private PdfService pdfService;

    /**
     * Gerar o PDF através do response mapeado para DTO para construir o PDF.
     * @param responseDto - DTO para construir o PDF
     * @param addGabarito - Possibilitar o usuário adicionar ou não o gabarito no PDF
     * @return - 200 OK - Retorna o PDF gerado
     */
    @PostMapping("/generate")
    public ResponseEntity<byte[]> downloadPdf(@RequestBody ResponseAlphaDto responseDto,
                                              @RequestParam(name = "addGabarito") Boolean addGabarito) {
        byte[] pdfBytes = pdfService.gerarPdf(responseDto, addGabarito);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "arquivo.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
