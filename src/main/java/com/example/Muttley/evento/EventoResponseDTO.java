package com.example.Muttley.evento;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoResponseDTO(
    Long id,
    String titulo,
    String descricao,
    LocalDate dataInicio,
    LocalTime horaInicio,
    LocalDate dataFim,
    LocalTime horaFim
) {}