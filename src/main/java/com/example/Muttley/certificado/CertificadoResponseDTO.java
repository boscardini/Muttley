package com.example.Muttley.certificado;

import java.time.LocalDate;

public record CertificadoResponseDTO(
    Long id, 
    String codigoAutenticacao, 
    Integer cargaHoraria, 
    LocalDate dataEmissao
) {}