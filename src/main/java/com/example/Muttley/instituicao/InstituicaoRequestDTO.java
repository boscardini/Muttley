package com.example.Muttley.instituicao;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record InstituicaoRequestDTO(
    @NotBlank String nome,
    @CNPJ String cnpj, // Validação específica para CNPJ
    @NotBlank String endereco
) {}