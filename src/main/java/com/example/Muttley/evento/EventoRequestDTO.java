package com.example.Muttley.evento;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EventoRequestDTO(
    @NotBlank(message = "O título é obrigatório") 
    String titulo,
    
    @NotBlank(message = "A descrição é obrigatória") 
    String descricao,
    List<Long> apresentadoresIds,

    LocalDate dataInicio,
    LocalTime horaInicio,
    LocalDate dataFim,
    LocalTime horaFim,
    
    @Min(value = 0, message = "A complexidade mínima é 0")
    @Max(value = 5, message = "A complexidade máxima é 5")
    Integer complexidade,
    
    boolean requerCheckout
    
) {}