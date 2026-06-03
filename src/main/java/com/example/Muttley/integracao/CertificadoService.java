package com.example.Muttley.integracao;

import com.example.Muttley.evento.Evento;
import com.example.Muttley.inscricao.Inscricao;
import com.example.Muttley.usuario.Usuario;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class CertificadoService {

    public byte[] gerarCertificadoPdf(Inscricao inscricao) throws Exception {
        ClassPathResource resource = new ClassPathResource("template_certificado.pdf");
        byte[] pdfTemplateBytes = resource.getContentAsByteArray();
        
        try (PDDocument document = Loader.loadPDF(pdfTemplateBytes);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            PDPage page = document.getPage(0);
            Evento evento = inscricao.getEvento();

            try (PDPageContentStream contentStream = new PDPageContentStream(
                    document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {

                PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                PDType1Font fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

                int margemEsquerda = 270;
                int xCentralizado = 340;
                
                // Parte superior do certificado: frase + nome (alinhado e legível)
                int yNome = 404; // desceu um pouco para abrir espaço para a frase acima
                int yFraseCertificado = 432;
                int yLinha1 = 360; 
                int yLinha2 = 335;
                int yData = 280;

                // Bloco de assinatura (alinhado à linha horizontal do template)
                // PDF: Y cresce para cima — imagem acima da linha, texto abaixo.
                int xAssinatura = 325;
                int yLinhaAssinatura = 158;
                int larguraAssinatura = 155;
                int alturaAssinatura = 40;
                int yImagemAssinatura = yLinhaAssinatura + 6;
                int yTextoAssinatura = yLinhaAssinatura - 14;

                // --- Frase acima do nome ---
                contentStream.beginText();
                contentStream.setFont(fontRegular, 12);
                contentStream.newLineAtOffset(xCentralizado, yFraseCertificado);
                contentStream.showText("Este certificado é concedido a");
                contentStream.endText();

                // --- Nome do participante ---
                contentStream.beginText();
                contentStream.setFont(fontBold, 24);
                contentStream.newLineAtOffset(xCentralizado, yNome);
                contentStream.showText(inscricao.getParticipante().getNome().toUpperCase());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(fontRegular, 14);
                contentStream.newLineAtOffset(margemEsquerda, yLinha1);
                String linha1 = String.format("Por participar do evento %s,", evento.getTitulo());
                contentStream.showText(linha1);
                contentStream.endText();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String data = evento.getDataInicio().format(formatter);
                
                LocalTime inicio = evento.getHoraInicio();
                LocalTime fim = evento.getHoraFim();
                
                String textoCargaHoraria = "0h";
                if (inicio != null && fim != null) {
                    Duration duracao = Duration.between(inicio, fim);
                    long horas = duracao.toHours();
                    long minutos = duracao.toMinutesPart(); 
                    
                    if (minutos > 0) {
                        textoCargaHoraria = String.format("%dh%02d", horas, minutos); 
                    } else {
                        textoCargaHoraria = String.format("%dh", horas); 
                    }
                }
                
                contentStream.beginText();
                contentStream.setFont(fontRegular, 14);
                contentStream.newLineAtOffset(margemEsquerda, yLinha2); 
                String linha2 = String.format("realizado no dia %s, com carga horária de %s.", data, textoCargaHoraria);
                contentStream.showText(linha2);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(fontRegular, 14);
                contentStream.newLineAtOffset(xCentralizado, yData); 
                String localData = String.format("São Paulo, %s", data);
                contentStream.showText(localData);
                contentStream.endText();

                Usuario gestor = evento.getGestorCriador();
                if (gestor != null && gestor.getAssinaturaBase64() != null && !gestor.getAssinaturaBase64().isBlank()) {
                    byte[] imgBytes = decodificarBase64(gestor.getAssinaturaBase64());
                    if (imgBytes.length > 0) {
                        PDImageXObject assinatura = PDImageXObject.createFromByteArray(document, imgBytes, "assinatura");
                        contentStream.drawImage(assinatura, xAssinatura, yImagemAssinatura, larguraAssinatura, alturaAssinatura);
                    }
                }

                String descricaoAssinatura = evento.getAssinaturaDescricao();
                if (descricaoAssinatura != null && !descricaoAssinatura.isBlank()) {
                    contentStream.beginText();
                    contentStream.setFont(fontRegular, 11);
                    contentStream.newLineAtOffset(xAssinatura, yTextoAssinatura);
                    contentStream.showText(descricaoAssinatura);
                    contentStream.endText();
                }
            }

            document.save(output);
            return output.toByteArray();
        }
    }

    private byte[] decodificarBase64(String base64) {
        String dados = base64.trim();
        if (dados.contains(",")) {
            dados = dados.substring(dados.indexOf(',') + 1);
        }
        try {
            return Base64.getDecoder().decode(dados);
        } catch (IllegalArgumentException e) {
            return new byte[0];
        }
    }
}
