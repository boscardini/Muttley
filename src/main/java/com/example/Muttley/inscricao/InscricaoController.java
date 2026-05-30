package com.example.Muttley.inscricao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Muttley.integracao.CertificadoService;

@RestController
@RequestMapping("/inscricoes")
@RequiredArgsConstructor
public class InscricaoController {

    private final InscricaoService service;
    private final InscricaoRepository inscricaoRepository;
    private final CertificadoService certificadoService;

    @PostMapping
    public ResponseEntity<InscricaoResponseDTO> inscrever(@RequestBody @Valid InscricaoRequestDTO dto) {
        return ResponseEntity.ok(service.realizarInscricao(dto));
    }

    @PatchMapping("/checkin/{eventoId}/cpf/{cpf}")
    public ResponseEntity<InscricaoResponseDTO> checkIn(
            @PathVariable Long eventoId, 
            @PathVariable String cpf) {
        return ResponseEntity.ok(service.realizarCheckIn(cpf, eventoId));
    }

    @PatchMapping("/checkout/{eventoId}/cpf/{cpf}")
    public ResponseEntity<InscricaoResponseDTO> checkOut(
            @PathVariable Long eventoId, 
            @PathVariable String cpf,
            @RequestParam String token) {
        return ResponseEntity.ok(service.realizarCheckOut(cpf, eventoId, token));
    }

    @GetMapping("/historico/cpf/{cpf}")
    public ResponseEntity<List<InscricaoResponseDTO>> buscarHistorico(@PathVariable String cpf) {
        return ResponseEntity.ok(service.buscarHistoricoDoAluno(cpf));
    }

    @GetMapping("/{id}/certificado")
    public ResponseEntity<byte[]> baixarCertificado(@PathVariable Long id) throws Exception {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new com.example.Muttley.infra.RegraDeNegocioException(
                        "Inscrição não encontrada para o ID fornecido."));

        if (inscricao.getStatus() != com.example.Muttley.inscricao.StatusInscricao.CONCLUIDO) {
            throw new com.example.Muttley.infra.RegraDeNegocioException(
                    "O certificado só fica disponível após a conclusão do evento (Check-out realizado).");
        }
        
        byte[] pdfBytes = certificadoService.gerarCertificadoPdf(inscricao);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=Certificado_" + id + ".pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @GetMapping("/participante/{participanteId}")
    public ResponseEntity<List<InscricaoResponseDTO>> listarPorParticipante(@PathVariable Long participanteId) {
        return ResponseEntity.ok(service.listarPorParticipante(participanteId));
    }

    @DeleteMapping("/evento/{eventoId}/participante/{participanteId}")
    public ResponseEntity<Void> cancelarInscricao(
            @PathVariable Long eventoId,
            @PathVariable Long participanteId) {
        service.cancelarInscricao(eventoId, participanteId);
        return ResponseEntity.noContent().build();
    }
}