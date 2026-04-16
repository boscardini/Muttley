package com.example.Muttley.certificado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CertificadoRequestDTO(
    @NotBlank String codigoAutenticacao,
    @NotNull Integer cargaHoraria,
    @NotNull LocalDate dataEmissao
) {}