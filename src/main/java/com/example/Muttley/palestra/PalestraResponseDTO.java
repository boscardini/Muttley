package com.example.Muttley.palestra;

public record PalestraResponseDTO(
    Long id, 
    String titulo, 
    String palestrante, 
    String codigo
) {}