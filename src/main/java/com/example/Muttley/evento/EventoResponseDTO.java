package com.example.Muttley.evento;

import com.example.Muttley.apresentador.ApresentadorResponseDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EventoResponseDTO(
    Long id,
    String titulo,
    String descricao,
    LocalDate dataInicio,
    LocalTime horaInicio,
    LocalDate dataFim,
    LocalTime horaFim,
    Integer complexidade,
    Boolean requerCheckout,
    String tokenCheckoutEstatico,
    String tokenCheckoutDinamico,
    List<ApresentadorResponseDTO> apresentadores,
    Long criadorId
) {}