package tech.pierandrei.SimulaProva.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import tech.pierandrei.SimulaProva.dto.alpha.PerguntaDto;
import tech.pierandrei.SimulaProva.dto.alpha.ResponseAlphaDto;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Serviço responsável pela geração de PDFs de simulados
 * Utiliza a biblioteca iText para criar documentos PDF formatados
 * com layout moderno e profissional
 */
@Service
public class PdfService {

    // CONSTANTES DE LAYOUT - Definem as dimensões e margens do documento
    private static final float PAGE_WIDTH = 595f;      // Largura padrão A4
    private static final float PAGE_HEIGHT = 842f;     // Altura padrão A4
    private static final float MARGIN = 30f;           // Margem das bordas
    private static final float CONTENT_WIDTH = PAGE_WIDTH - (2 * MARGIN); // Área útil

    // PALETA DE CORES - Define o esquema visual do documento
    private static final BaseColor PRIMARY_COLOR = new BaseColor(102, 126, 234);   // Azul principal
    private static final BaseColor SECONDARY_COLOR = new BaseColor(118, 75, 162);  // Roxo secundário
    private static final BaseColor TEXT_COLOR = new BaseColor(55, 65, 81);         // Cinza escuro para texto
    private static final BaseColor LIGHT_GRAY = new BaseColor(249, 250, 251);      // Cinza claro para fundos
    private static final BaseColor BORDER_COLOR = new BaseColor(229, 231, 235);    // Cinza para bordas

    /**
     * Método principal para geração do PDF
     * @param responseDto Dados do simulado (perguntas, tema, dificuldade)
     * @param adicionarGabarito Flag para incluir ou não o gabarito
     * @return Array de bytes do PDF gerado
     */
    public byte[] gerarPdf(ResponseAlphaDto responseDto, Boolean adicionarGabarito) {
        try {
            // Configuração inicial do documento
            Document document = new Document(PageSize.A4, MARGIN, MARGIN, MARGIN, MARGIN);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            // Configurar eventos customizados para header/footer
            ModernHeaderFooter headerFooter = new ModernHeaderFooter();
            writer.setPageEvent(headerFooter);

            document.open();

            // TEMPLATE DE FUNDO - Aplica um PDF template como background
            // ATENÇÃO: Este recurso precisa do arquivo "/template.pdf" no resources
            PdfReader templateReader = new PdfReader(getClass().getResourceAsStream("/template.pdf"));
            PdfImportedPage templatePage = writer.getImportedPage(templateReader, 1);
            PdfContentByte background = writer.getDirectContentUnder();
            background.addTemplate(templatePage, 0, 0);

            // CONFIGURAÇÃO DE FONTES - Cria diferentes estilos tipográficos
            FontFactory.registerDirectories();
            Font titleFont = createFont(14, Font.BOLD, PRIMARY_COLOR);        // Títulos principais
            Font headerFont = createFont(12, Font.BOLD, TEXT_COLOR);          // Cabeçalhos
            Font bodyFont = createFont(10, Font.NORMAL, TEXT_COLOR);          // Texto comum
            Font questionFont = createFont(10, Font.BOLD, TEXT_COLOR);        // Perguntas
            Font alternativeFont = createFont(9, Font.NORMAL, TEXT_COLOR);    // Alternativas

            // MONTAGEM DO CONTEÚDO
            adicionarCabecalhoModerno(document, responseDto, titleFont, bodyFont);

            // Processa cada pergunta e monta o gabarito
            Map<Integer, String> gabarito = new HashMap<>();
            int numeroQuestao = 1;

            for (PerguntaDto pergunta : responseDto.perguntas()) {
                adicionarQuestaoModerna(document, pergunta, numeroQuestao, questionFont, alternativeFont, gabarito);
                numeroQuestao++;
            }

            // Adiciona gabarito se solicitado
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

    /**
     * Factory method para criar fontes com tratamento de erro
     * Implementa fallback para fonte padrão caso a fonte Helvetica falhe
     */
    private Font createFont(int size, int style, BaseColor color) {
        try {
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont, size, style);
            font.setColor(color);
            return font;
        } catch (Exception e) {
            // Fallback para fonte padrão em caso de erro
            Font font = FontFactory.getFont(FontFactory.HELVETICA, size, style);
            font.setColor(color);
            return font;
        }
    }

    /**
     * Cria cabeçalho moderno em layout de tabela
     * Layout: [SIMULADO] [Tema] [Nível de Dificuldade]
     */
    private void adicionarCabecalhoModerno(Document document, ResponseAlphaDto responseDto, Font titleFont, Font bodyFont)
            throws DocumentException {

        // Tabela de 3 colunas para organizar as informações
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{2, 1, 1}); // Proporções das colunas

        // Coluna 1: Título "SIMULADO"
        PdfPCell titleCell = new PdfPCell();
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        Paragraph title = new Paragraph("SIMULADO", createFont(16, Font.BOLD, PRIMARY_COLOR));
        titleCell.addElement(title);
        headerTable.addCell(titleCell);

        // Coluna 2: Tema da prova
        PdfPCell temaCell = new PdfPCell();
        temaCell.setBorder(Rectangle.NO_BORDER);
        temaCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        Paragraph tema = new Paragraph(responseDto.tema_da_pergunta(), createFont(9, Font.NORMAL, TEXT_COLOR));
        temaCell.addElement(tema);
        headerTable.addCell(temaCell);

        // Coluna 3: Nível de dificuldade
        PdfPCell dificuldadeCell = new PdfPCell();
        dificuldadeCell.setBorder(Rectangle.NO_BORDER);
        dificuldadeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        Paragraph dificuldade = new Paragraph("Nível: " + responseDto.dificuldadeDaPergunta().getDisplayName(),
                createFont(9, Font.NORMAL, TEXT_COLOR));
        dificuldadeCell.addElement(dificuldade);
        headerTable.addCell(dificuldadeCell);

        document.add(headerTable);

        // Linha separadora decorativa
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

    /**
     * Renderiza o gabarito em formato compacto (uma linha)
     * Formato: "1: A | 2: B | 3: C ..."
     */
    private void adicionarGabaritoCompacto(Document document, Map<Integer, String> gabarito,
                                           Font headerFont, Font bodyFont) throws DocumentException {

        // Separador visual antes do gabarito
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

        // Título da seção
        Paragraph gabaritoTitle = new Paragraph("GABARITO",
                createFont(12, Font.BOLD, PRIMARY_COLOR));
        gabaritoTitle.setSpacingAfter(8);
        document.add(gabaritoTitle);

        // Concatena todas as respostas em uma única linha
        StringBuilder respostasLinha = new StringBuilder();
        for (Map.Entry<Integer, String> entry : gabarito.entrySet()) {
            if (respostasLinha.length() > 0) {
                respostasLinha.append("  |  "); // Separador visual
            }
            respostasLinha.append(entry.getKey()).append(": ").append(entry.getValue());
        }

        Paragraph respostaParagraph = new Paragraph(respostasLinha.toString(),
                createFont(10, Font.NORMAL, TEXT_COLOR));
        document.add(respostaParagraph);
    }

    /**
     * Método vazio - provavelmente era usado para separadores visuais
     * Foi removido para economizar espaço no layout
     */
    private void adicionarSeparador(Document document) throws DocumentException {
        // Método removido para economizar espaço
    }

    /**
     * Renderiza uma questão individual com suas alternativas
     * @param pergunta Dados da pergunta
     * @param numero Número sequencial da questão
     * @param gabarito Map para armazenar a resposta correta
     */
    private void adicionarQuestaoModerna(Document document, PerguntaDto pergunta, int numero,
                                         Font questionFont, Font alternativeFont, Map<Integer, String> gabarito)
            throws DocumentException {

        // Parágrafo da pergunta com numeração
        Paragraph questionParagraph = new Paragraph();
        questionParagraph.setSpacingBefore(8);
        questionParagraph.setSpacingAfter(6);

        // Chunk do número (colorido) + Chunk do texto da pergunta
        Chunk numeroChunk = new Chunk(numero + ". ", createFont(11, Font.BOLD, PRIMARY_COLOR));
        Chunk perguntaChunk = new Chunk(pergunta.pergunta(), createFont(10, Font.NORMAL, TEXT_COLOR));

        questionParagraph.add(numeroChunk);
        questionParagraph.add(perguntaChunk);
        document.add(questionParagraph);

        // Renderiza cada alternativa (A, B, C, D)
        char letraAlternativa = 'A';
        for (String alternativa : pergunta.alternativas()) {
            Paragraph altParagraph = new Paragraph();
            altParagraph.setIndentationLeft(15); // Indentação para hierarquia visual
            altParagraph.setSpacingAfter(3);

            // REGEX para limpar letras duplicadas que possam vir dos dados
            // Remove padrões como "A) " no início da string
            String resultado = alternativa.replaceFirst("^[A-D]\\)\\s*", "");

            Chunk letraChunk = new Chunk(letraAlternativa + ") ",
                    createFont(9, Font.BOLD, SECONDARY_COLOR));
            Chunk textoChunk = new Chunk(resultado, createFont(9, Font.NORMAL, TEXT_COLOR));

            altParagraph.add(letraChunk);
            altParagraph.add(textoChunk);
            document.add(altParagraph);

            letraAlternativa++;
        }

        // Armazena a resposta correta no mapa de gabarito
        gabarito.put(numero, pergunta.gabarito());
    }

    /**
     * Versão alternativa do gabarito em formato de grid/tabela
     * Esta versão não está sendo utilizada no fluxo principal
     */
    private void adicionarGabaritoModerno(Document document, Map<Integer, String> gabarito,
                                          Font headerFont, Font bodyFont) throws DocumentException {

        // Título centralizado
        Paragraph gabaritoTitle = new Paragraph("GABARITO",
                createFont(14, Font.BOLD, PRIMARY_COLOR));
        gabaritoTitle.setAlignment(Element.ALIGN_CENTER);
        gabaritoTitle.setSpacingAfter(15);
        document.add(gabaritoTitle);

        // Grid de respostas (máximo 6 colunas)
        int colunas = Math.min(6, gabarito.size());
        PdfPTable gabaritoTable = new PdfPTable(colunas);
        gabaritoTable.setWidthPercentage(90);
        gabaritoTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        gabaritoTable.setSpacingBefore(10);

        // Preenche cada célula do grid
        for (Map.Entry<Integer, String> entry : gabarito.entrySet()) {
            PdfPCell gabaritoCell = new PdfPCell();
            gabaritoCell.setPadding(8);
            gabaritoCell.setBorderColor(BORDER_COLOR);
            gabaritoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            gabaritoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // Alternância de cores para melhor legibilidade
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

    /**
     * Classe interna para customizar header e footer de cada página
     * Implementa PdfPageEventHelper para interceptar eventos de página
     */
    private static class ModernHeaderFooter extends PdfPageEventHelper {
        private Font footerFont;

        public ModernHeaderFooter() {
            try {
                BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                footerFont = new Font(baseFont, 8, Font.NORMAL);
                footerFont.setColor(new BaseColor(107, 114, 128)); // Cinza suave
            } catch (Exception e) {
                footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            }
        }

        /**
         * Evento disparado ao final de cada página
         * Adiciona rodapé com numeração e linha decorativa no topo
         */
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte cb = writer.getDirectContent();

                // Rodapé centralizado com nome da aplicação e número da página
                Phrase footer = new Phrase("SimulaProva - Página " + writer.getPageNumber(), footerFont);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
                        (document.right() - document.left()) / 2 + document.leftMargin(),
                        document.bottom() - 10, 0);

                // Linha decorativa colorida no topo da página
                cb.setColorStroke(new BaseColor(102, 126, 234, 50)); // Azul com transparência
                cb.setLineWidth(2f);
                cb.moveTo(document.left(), document.top() + 10);
                cb.lineTo(document.right(), document.top() + 10);
                cb.stroke();

            } catch (Exception e) {
                // Ignora erros silenciosamente - poderia implementar logging aqui
            }
        }
    }
}
