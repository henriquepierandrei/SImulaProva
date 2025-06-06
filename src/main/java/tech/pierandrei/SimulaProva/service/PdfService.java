package tech.pierandrei.SimulaProva.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import tech.pierandrei.SimulaProva.dto.PerguntaDto;
import tech.pierandrei.SimulaProva.dto.ResponseDto;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfService {

    private static final float PAGE_WIDTH = 595f;
    private static final float PAGE_HEIGHT = 842f;
    private static final float MARGIN = 30f;
    private static final float CONTENT_WIDTH = PAGE_WIDTH - (2 * MARGIN);

    // Iniciando as cores
    private static final BaseColor PRIMARY_COLOR = new BaseColor(102, 126, 234); // #667eea
    private static final BaseColor SECONDARY_COLOR = new BaseColor(118, 75, 162); // #764ba2
    private static final BaseColor TEXT_COLOR = new BaseColor(55, 65, 81); // #374151
    private static final BaseColor LIGHT_GRAY = new BaseColor(249, 250, 251); // #f9fafb
    private static final BaseColor BORDER_COLOR = new BaseColor(229, 231, 235); // #e5e7eb



    public byte[] gerarPdf(ResponseDto responseDto, Boolean adicionarGabarito) {
        try {
            Document document = new Document(PageSize.A4, MARGIN, MARGIN, MARGIN, MARGIN);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            // Configurar eventos para header/footer
            ModernHeaderFooter headerFooter = new ModernHeaderFooter();
            writer.setPageEvent(headerFooter);

            document.open();

            // Insere o PDF como template
            PdfReader templateReader = new PdfReader(getClass().getResourceAsStream("/template.pdf"));
            PdfImportedPage templatePage = writer.getImportedPage(templateReader, 1);
            PdfContentByte background = writer.getDirectContentUnder();
            background.addTemplate(templatePage, 0, 0);

            // Criar fontes compactas
            FontFactory.registerDirectories();
            Font titleFont = createFont(14, Font.BOLD, PRIMARY_COLOR);
            Font headerFont = createFont(12, Font.BOLD, TEXT_COLOR);
            Font bodyFont = createFont(10, Font.NORMAL, TEXT_COLOR);
            Font questionFont = createFont(10, Font.BOLD, TEXT_COLOR);
            Font alternativeFont = createFont(9, Font.NORMAL, TEXT_COLOR);

            // Header compacto
            adicionarCabecalhoModerno(document, responseDto, titleFont, bodyFont);

            // Perguntas
            Map<Integer, String> gabarito = new HashMap<>();
            int numeroQuestao = 1;

            for (PerguntaDto pergunta : responseDto.perguntas()) {
                adicionarQuestaoModerna(document, pergunta, numeroQuestao, questionFont, alternativeFont, gabarito);
                numeroQuestao++;
            }

            // Adicionar Gabarito
            if (adicionarGabarito) {
                document.add(new Paragraph("\n"));
                adicionarGabaritoCompacto(document, gabarito, headerFont, bodyFont);
            }

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF moderno", e);
        }
    }

    private Font createFont(int size, int style, BaseColor color) {
        try {
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont, size, style);
            font.setColor(color);
            return font;
        } catch (Exception e) {
            // Fallback para fonte padrão
            Font font = FontFactory.getFont(FontFactory.HELVETICA, size, style);
            font.setColor(color);
            return font;
        }
    }

    private void adicionarCabecalhoModerno(Document document, ResponseDto responseDto, Font titleFont, Font bodyFont)
            throws DocumentException {

        // Header compacto em uma linha
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{2, 1, 1});

        // Título
        PdfPCell titleCell = new PdfPCell();
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        Paragraph title = new Paragraph("SIMULADO", createFont(16, Font.BOLD, PRIMARY_COLOR));
        titleCell.addElement(title);
        headerTable.addCell(titleCell);

        // Tema
        PdfPCell temaCell = new PdfPCell();
        temaCell.setBorder(Rectangle.NO_BORDER);
        temaCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        Paragraph tema = new Paragraph(responseDto.tema_da_pergunta(), createFont(9, Font.NORMAL, TEXT_COLOR));
        temaCell.addElement(tema);
        headerTable.addCell(temaCell);

        // Dificuldade
        PdfPCell dificuldadeCell = new PdfPCell();
        dificuldadeCell.setBorder(Rectangle.NO_BORDER);
        dificuldadeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        Paragraph dificuldade = new Paragraph("Nível: " + responseDto.dificuldadeDaPergunta().getDisplayName(),
                createFont(9, Font.NORMAL, TEXT_COLOR));
        dificuldadeCell.addElement(dificuldade);
        headerTable.addCell(dificuldadeCell);

        document.add(headerTable);

        // Separador fino
        PdfPTable separator = new PdfPTable(1);
        separator.setWidthPercentage(100);
        separator.setSpacingBefore(8);
        separator.setSpacingAfter(12);

        PdfPCell separatorCell = new PdfPCell();
        separatorCell.setBorder(Rectangle.NO_BORDER);
        separatorCell.setFixedHeight(1);
        separatorCell.setBackgroundColor(BORDER_COLOR);
        separator.addCell(separatorCell);
        document.add(separator);
    }

    private void adicionarGabaritoCompacto(Document document, Map<Integer, String> gabarito,
                                           Font headerFont, Font bodyFont) throws DocumentException {

        // Separador para gabarito
        PdfPTable separator = new PdfPTable(1);
        separator.setWidthPercentage(100);
        separator.setSpacingBefore(15);
        separator.setSpacingAfter(8);

        PdfPCell separatorCell = new PdfPCell();
        separatorCell.setBorder(Rectangle.NO_BORDER);
        separatorCell.setFixedHeight(1);
        separatorCell.setBackgroundColor(BORDER_COLOR);
        separator.addCell(separatorCell);
        document.add(separator);

        // Título compacto
        Paragraph gabaritoTitle = new Paragraph("GABARITO",
                createFont(12, Font.BOLD, PRIMARY_COLOR));
        gabaritoTitle.setSpacingAfter(8);
        document.add(gabaritoTitle);

        // Respostas em linha
        StringBuilder respostasLinha = new StringBuilder();
        for (Map.Entry<Integer, String> entry : gabarito.entrySet()) {
            if (respostasLinha.length() > 0) {
                respostasLinha.append("  |  ");
            }
            respostasLinha.append(entry.getKey()).append(": ").append(entry.getValue());
        }

        Paragraph respostaParagraph = new Paragraph(respostasLinha.toString(),
                createFont(10, Font.NORMAL, TEXT_COLOR));
        document.add(respostaParagraph);
    }

    private void adicionarSeparador(Document document) throws DocumentException {
        // Método removido para economizar espaço
    }

    private void adicionarQuestaoModerna(Document document, PerguntaDto pergunta, int numero,
                                         Font questionFont, Font alternativeFont, Map<Integer, String> gabarito)
            throws DocumentException {

        // Pergunta em formato compacto
        Paragraph questionParagraph = new Paragraph();
        questionParagraph.setSpacingBefore(8);
        questionParagraph.setSpacingAfter(6);

        // Número da questão
        Chunk numeroChunk = new Chunk(numero + ". ", createFont(11, Font.BOLD, PRIMARY_COLOR));
        Chunk perguntaChunk = new Chunk(pergunta.pergunta(), createFont(10, Font.NORMAL, TEXT_COLOR));

        questionParagraph.add(numeroChunk);
        questionParagraph.add(perguntaChunk);
        document.add(questionParagraph);

        // Alternativas compactas
        char letraAlternativa = 'A';
        for (String alternativa : pergunta.alternativas()) {
            Paragraph altParagraph = new Paragraph();
            altParagraph.setIndentationLeft(15);
            altParagraph.setSpacingAfter(3);

            Chunk letraChunk = new Chunk(letraAlternativa + ") ",
                    createFont(9, Font.BOLD, SECONDARY_COLOR));
            Chunk textoChunk = new Chunk(alternativa, createFont(9, Font.NORMAL, TEXT_COLOR));

            altParagraph.add(letraChunk);
            altParagraph.add(textoChunk);
            document.add(altParagraph);

            letraAlternativa++;
        }

        // Armazenar gabarito
        gabarito.put(numero, pergunta.gabarito());
    }

    private void adicionarGabaritoModerno(Document document, Map<Integer, String> gabarito,
                                          Font headerFont, Font bodyFont) throws DocumentException {

        // Título do gabarito
        Paragraph gabaritoTitle = new Paragraph("GABARITO",
                createFont(14, Font.BOLD, PRIMARY_COLOR));
        gabaritoTitle.setAlignment(Element.ALIGN_CENTER);
        gabaritoTitle.setSpacingAfter(15);
        document.add(gabaritoTitle);

        // Grid compacto de respostas
        int colunas = Math.min(6, gabarito.size()); // Máximo 6 colunas
        PdfPTable gabaritoTable = new PdfPTable(colunas);
        gabaritoTable.setWidthPercentage(90);
        gabaritoTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        gabaritoTable.setSpacingBefore(10);

        for (Map.Entry<Integer, String> entry : gabarito.entrySet()) {
            PdfPCell gabaritoCell = new PdfPCell();
            gabaritoCell.setPadding(8);
            gabaritoCell.setBorderColor(BORDER_COLOR);
            gabaritoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            gabaritoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            if (entry.getKey() % 2 == 0) {
                gabaritoCell.setBackgroundColor(LIGHT_GRAY);
            }

            Paragraph respostaParagraph = new Paragraph();
            Chunk numeroChunk = new Chunk(entry.getKey() + ": ",
                    createFont(9, Font.BOLD, TEXT_COLOR));
            Chunk respostaChunk = new Chunk(entry.getValue(),
                    createFont(11, Font.BOLD, PRIMARY_COLOR));

            respostaParagraph.add(numeroChunk);
            respostaParagraph.add(respostaChunk);
            respostaParagraph.setAlignment(Element.ALIGN_CENTER);

            gabaritoCell.addElement(respostaParagraph);
            gabaritoTable.addCell(gabaritoCell);
        }

        document.add(gabaritoTable);
    }

    // Classe para header e footer personalizados
    private static class ModernHeaderFooter extends PdfPageEventHelper {
        private Font footerFont;

        public ModernHeaderFooter() {
            try {
                BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                footerFont = new Font(baseFont, 8, Font.NORMAL);
                footerFont.setColor(new BaseColor(107, 114, 128)); // text-gray-500
            } catch (Exception e) {
                footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                // Rodapé com número da página
                PdfContentByte cb = writer.getDirectContent();
                Phrase footer = new Phrase("SimulaProva - Página " + writer.getPageNumber(), footerFont);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
                        (document.right() - document.left()) / 2 + document.leftMargin(),
                        document.bottom() - 10, 0);

                // Linha decorativa no topo
                cb.setColorStroke(new BaseColor(102, 126, 234, 50)); // PRIMARY_COLOR com transparência
                cb.setLineWidth(2f);
                cb.moveTo(document.left(), document.top() + 10);
                cb.lineTo(document.right(), document.top() + 10);
                cb.stroke();

            } catch (Exception e) {
                // Log do erro se necessário
            }
        }
    }
}