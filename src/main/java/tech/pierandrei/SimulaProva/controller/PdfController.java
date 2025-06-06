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
import tech.pierandrei.SimulaProva.dto.ResponseDto;
import tech.pierandrei.SimulaProva.service.PdfService;

@RestController
@RequestMapping("/pdf")
public class PdfController {
    @Autowired
    private PdfService pdfService;


    @PostMapping("/generate")
    public ResponseEntity<byte[]> downloadPdf(@RequestBody ResponseDto responseDto,
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
