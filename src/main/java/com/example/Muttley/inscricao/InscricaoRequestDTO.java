package com.example.Muttley.inscricao;

import jakarta.validation.constraints.NotNull;

public record InscricaoRequestDTO(
    @NotNull(message = "O ID do participante é obrigatório")
    Long participanteId,
    
    @NotNull(message = "O ID do evento é obrigatório")
    Long eventoId
) {}