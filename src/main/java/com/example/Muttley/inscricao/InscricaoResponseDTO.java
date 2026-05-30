package com.example.Muttley.inscricao;

import java.time.LocalDateTime;

public record InscricaoResponseDTO(
    Long id,
    Long participanteId,
    String nomeParticipante,
    Long eventoId,
    String tituloEvento,
    String status,
    LocalDateTime dataHoraCheckIn,
    LocalDateTime dataHoraCheckOut,
    Integer pontosRecebidos
) {}