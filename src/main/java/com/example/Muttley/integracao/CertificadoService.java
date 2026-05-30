package com.example.Muttley.integracao;

import com.example.Muttley.inscricao.Inscricao;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class CertificadoService {

    public byte[] gerarCertificadoPdf(Inscricao inscricao) throws Exception {
        ClassPathResource resource = new ClassPathResource("template_certificado.pdf");
        byte[] pdfTemplateBytes = resource.getContentAsByteArray();
        
        try (PDDocument document = Loader.loadPDF(pdfTemplateBytes);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            PDPage page = document.getPage(0);

            try (PDPageContentStream contentStream = new PDPageContentStream(
                    document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {

                PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                PDType1Font fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

                // --- COORDENADAS AJUSTADAS ---
                int margemEsquerda = 270; // Para o parágrafo (alinhado à esquerda)
                int xCentralizado = 340;  // Empurra o Nome e a Data mais para a direita (centro visual)
                
                int yNome = 410; // Aumentamos de 340 para 410 (Sobe em direção ao título)
                int yLinha1 = 360; 
                int yLinha2 = 335;
                int yData = 280; 

                // --- 1. NOME DO ALUNO (Mais centralizado e mais alto) ---
                contentStream.beginText();
                contentStream.setFont(fontBold, 24);
                contentStream.newLineAtOffset(xCentralizado, yNome); 
                contentStream.showText(inscricao.getParticipante().getNome().toUpperCase());
                contentStream.endText();

                // --- 2. LINHA 1: NOME DO EVENTO ---
                contentStream.beginText();
                contentStream.setFont(fontRegular, 14);
                contentStream.newLineAtOffset(margemEsquerda, yLinha1);
                String linha1 = String.format("Por participar do evento %s,", inscricao.getEvento().getTitulo());
                contentStream.showText(linha1);
                contentStream.endText();

                // --- 3. LINHA 2: CARGA HORÁRIA E DATA ---
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String data = inscricao.getEvento().getDataInicio().format(formatter);
                
                LocalTime inicio = inscricao.getEvento().getHoraInicio();
                LocalTime fim = inscricao.getEvento().getHoraFim();
                
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

                // --- 4. LOCAL E DATA (Mais centralizado e mais alto) ---
                contentStream.beginText();
                contentStream.setFont(fontRegular, 14);
                contentStream.newLineAtOffset(xCentralizado, yData); 
                String localData = String.format("São Paulo, %s", data);
                contentStream.showText(localData);
                contentStream.endText();
            }

            document.save(output);
            return output.toByteArray();
        }
    }
}