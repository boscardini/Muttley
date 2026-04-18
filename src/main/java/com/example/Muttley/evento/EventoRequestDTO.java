package com.example.Muttley.evento;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

public record EventoRequestDTO(
    @NotBlank(message = "O título é obrigatório") 
    String titulo,
    
    @NotBlank(message = "A descrição é obrigatória") 
    String descricao,
    
    LocalDate dataInicio,
    LocalTime horaInicio,
    LocalDate dataFim,
    LocalTime horaFim
) {}