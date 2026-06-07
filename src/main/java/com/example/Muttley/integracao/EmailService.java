package com.example.Muttley.integracao;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // 1. E-mail de confirmação de RSVP
    public void enviarConfirmacaoInscricao(String para, String nomeParticipante, String tituloEvento) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(para);
        mensagem.setSubject("Inscrição Confirmada: " + tituloEvento);
        mensagem.setText("Olá, " + nomeParticipante + "!\n\n" +
                "Sua inscrição para o evento '" + tituloEvento + "' está confirmada.\n\n" +
                "Nos vemos lá!");
        
        mailSender.send(mensagem);
    }

    // 2. E-mail com o Certificado em PDF anexado
    public void enviarCertificadoPdf(String para, String nomeParticipante, String tituloEvento, byte[] pdfBytes) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);

            helper.setTo(para);
            helper.setSubject("Seu Certificado: " + tituloEvento);
            helper.setText("Olá, " + nomeParticipante + "!\n\n" +
                    "Parabéns por concluir o evento '" + tituloEvento + "'.\n" +
                    "Seu certificado oficial está anexado a este e-mail.\n\n" +
                    "Continue evoluindo e ganhando XP na plataforma Muttley!");

            String nomeArquivo = "Certificado.pdf";
            helper.addAttachment(nomeArquivo, new ByteArrayResource(pdfBytes));

            mailSender.send(mensagem);
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail com certificado: " + e.getMessage());
        }
    }
}