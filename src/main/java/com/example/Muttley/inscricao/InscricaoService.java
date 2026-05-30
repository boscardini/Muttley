package com.example.Muttley.inscricao;

import com.example.Muttley.evento.Evento;
import com.example.Muttley.evento.EventoRepository;
import com.example.Muttley.infra.RegraDeNegocioException;
import com.example.Muttley.integracao.CertificadoService;
import com.example.Muttley.integracao.EmailService;
import com.example.Muttley.participante.Participante;
import com.example.Muttley.participante.ParticipanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final ParticipanteRepository participanteRepository;
    private final EventoRepository eventoRepository;
    private final InscricaoMapper mapper;
    private final EmailService emailService;
    private final CertificadoService certificadoService;

    @Transactional
    public InscricaoResponseDTO realizarInscricao(InscricaoRequestDTO dto) {
        // Lógica de inscrição permanece idêntica...
        Participante participante = participanteRepository.findById(dto.participanteId())
                .orElseThrow(() -> new RegraDeNegocioException("Participante não encontrado"));
        Evento evento = eventoRepository.findById(dto.eventoId())
                .orElseThrow(() -> new RegraDeNegocioException("Evento não encontrado"));

        if (inscricaoRepository.existsByParticipanteIdAndEventoId(participante.getId(), evento.getId())) {
            throw new RegraDeNegocioException("Você já está inscrito neste evento!");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setParticipante(participante);
        inscricao.setEvento(evento);
        inscricao.setStatus(StatusInscricao.INSCRITO);

        Inscricao inscricaoSalva = inscricaoRepository.save(inscricao);
        emailService.enviarConfirmacaoInscricao(participante.getEmail(), participante.getNome(), evento.getTitulo());
        return mapper.toDto(inscricaoSalva);
    }

    @Transactional
    public InscricaoResponseDTO realizarCheckIn(String cpf, Long eventoId) {
        Participante participante = participanteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraDeNegocioException("CPF não cadastrado"));

        Inscricao inscricao = inscricaoRepository.findByParticipanteIdAndEventoId(participante.getId(), eventoId)
                .orElseThrow(() -> new RegraDeNegocioException("Participante não está inscrito neste evento"));

        if (inscricao.getStatus() != StatusInscricao.INSCRITO) {
            throw new RegraDeNegocioException("Check-in já realizado ou participação já concluída.");
        }

        inscricao.setDataHoraCheckIn(LocalDateTime.now());

        // ---> EVENTO SEM CHECKOUT EXIGIDO <---
        if (!inscricao.getEvento().isRequerCheckout()) {
            inscricao.setStatus(StatusInscricao.CONCLUIDO);
            inscricao.setDataHoraCheckOut(LocalDateTime.now()); // Conclui na hora
            
            // Ganha os pontos cheios baseados no tempo planejado original do evento
            int xpGanho = calcularXpPlanejadoDoEvento(inscricao.getEvento());
            inscricao.setPontosRecebidos(xpGanho);
            
            participante.setPontosTotais(participante.getPontosTotais() + xpGanho);
            participanteRepository.save(participante);
        } else {
            // Fluxo normal com obrigatoriedade de saída posterior
            inscricao.setStatus(StatusInscricao.CHECK_IN_REALIZADO);
        }

        return mapper.toDto(inscricaoRepository.save(inscricao));
    }

    @Transactional
    public InscricaoResponseDTO realizarCheckOut(String cpf, Long eventoId, String tokenDigitado) {
        Participante participante = participanteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraDeNegocioException("CPF não cadastrado"));

        Inscricao inscricao = inscricaoRepository.findByParticipanteIdAndEventoId(participante.getId(), eventoId)
                .orElseThrow(() -> new RegraDeNegocioException("Inscrição não encontrada"));

        if (!inscricao.getEvento().isRequerCheckout()) {
            throw new RegraDeNegocioException("Este evento não requer validação de check-out.");
        }

        if (inscricao.getStatus() != StatusInscricao.CHECK_IN_REALIZADO) {
            throw new RegraDeNegocioException("Não é possível fazer check-out sem um check-in ativo.");
        }

        // ---> VALIDAÇÃO COM TOKEN DINÂMICO OU ESTÁTICO (BACKUP) <---
        String tokenEstatico = inscricao.getEvento().getTokenCheckoutEstatico();
        String tokenDinamico = inscricao.getEvento().getTokenCheckoutDinamico();

        if (!tokenDigitado.equals(tokenEstatico) && !tokenDigitado.equals(tokenDinamico)) {
            throw new RegraDeNegocioException("QR Code inválido ou expirado!");
        }

        inscricao.setDataHoraCheckOut(LocalDateTime.now());
        inscricao.setStatus(StatusInscricao.CONCLUIDO);

        int xpGanho = calcularXpComTetoMaximo(inscricao);
        inscricao.setPontosRecebidos(xpGanho);

        participante.setPontosTotais(participante.getPontosTotais() + xpGanho);
        participanteRepository.save(participante);
        Inscricao inscricaoSalva = inscricaoRepository.save(inscricao);

        try {
            byte[] pdfBytes = certificadoService.gerarCertificadoPdf(inscricaoSalva);
            
            emailService.enviarCertificadoPdf(
                    participante.getEmail(), 
                    participante.getNome(), 
                    inscricaoSalva.getEvento().getTitulo(), 
                    pdfBytes
            );
        } catch (Exception e) {
            System.err.println("Falha ao gerar/enviar certificado: " + e.getMessage());
        }

        return mapper.toDto(inscricaoSalva);
    }

    public List<InscricaoResponseDTO> buscarHistoricoDoAluno(String cpf) {
        if (participanteRepository.findByCpf(cpf).isEmpty()) {
            throw new RegraDeNegocioException("CPF não cadastrado");
        }

        return inscricaoRepository.findAllByParticipanteCpfOrderByDataHoraCheckInDesc(cpf)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // --- AUXILIAR 1: Calcula o tempo planejado total do evento (Usa para eventos sem checkout) ---
    private int calcularXpPlanejadoDoEvento(Evento evento) {
        if (evento.getHoraInicio() == null || evento.getHoraFim() == null) return 10;
        
        long minutosPlanejados = Duration.between(evento.getHoraInicio(), evento.getHoraFim()).toMinutes();
        return converterMinutosParaXp(minutosPlanejados, evento.getComplexidade());
    }

    // --- AUXILIAR 2: Calcula o tempo real limitando pelo teto máximo agendado ---
    private int calcularXpComTetoMaximo(Inscricao inscricao) {
        Evento evento = inscricao.getEvento();
        
        // 1. Tempo total máximo permitido (Estipulado na criação)
        long minutosPlanejados = Duration.between(evento.getHoraInicio(), evento.getHoraFim()).toMinutes();
        
        // 2. Tempo que o aluno de fato ficou presente
        long minutosReais = Duration.between(inscricao.getDataHoraCheckIn(), inscricao.getDataHoraCheckOut()).toMinutes();
        
        // ---> A MÁGICA DO TETO: Pega o menor valor entre o real e o planejado <---
        long minutosEfetivos = Math.min(minutosReais, minutosPlanejados);
        
        return converterMinutosParaXp(minutosEfetivos, evento.getComplexidade());
    }

    // --- AUXILIAR 3: Fórmula compartilhada de conversão ---
    private int converterMinutosParaXp(long minutos, int complexidade) {
        if (minutos <= 0) minutos = 1;
        
        double horas = minutos / 60.0;
        double xpBase = horas * 10.0;
        double multiplicadorBonus = complexidade * 0.10;
        
        return (int) Math.round(xpBase + (xpBase * multiplicadorBonus));
    }
}