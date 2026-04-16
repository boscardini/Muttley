package com.example.Muttley.palestra;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record PalestraResponseDTO(
    Long id, 
    String titulo, 
    String palestrante, 
    String codigo, 
    String descricao,
    List<String> habilidades
    //LocalDate dataInicio,
    //LocalDate dataFim,
    //LocalTime horaInicio,
    //LocalTime horaFim
    
) {}