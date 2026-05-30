package com.example.Muttley.evento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.example.Muttley.apresentador.ApresentadorResponseDTO;

public record EventoResponseDTO(
    Long id,
    String titulo,
    String descricao,
    List<ApresentadorResponseDTO> apresentadores,
    LocalDate dataInicio,
    LocalTime horaInicio,
    LocalDate dataFim,
    LocalTime horaFim,
    Integer complexidade,
    boolean requerCheckout,
    String tokenCheckoutEstatico,
    String tokenCheckoutDinamico
) {}