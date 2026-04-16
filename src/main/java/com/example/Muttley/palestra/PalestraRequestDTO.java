package com.example.Muttley.palestra;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record PalestraRequestDTO(
    @NotBlank String titulo,
    @NotBlank String palestrante,
    @NotBlank String codigo,
    @NotBlank String descricao,
    @NotBlank List<String> habilidades
//    @NotBlank private String dataInicio ,
//    @NotBlank private String horaInicio ,
//    @NotBlank private String dataFim ,
//    @NotBlank private String horaFim 
) {}