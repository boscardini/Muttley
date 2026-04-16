package com.example.Muttley.Palestrante;

import jakarta.validation.constraints.NotBlank;

public record AtualizacaoPalestrante(Long id,

		@NotBlank(message = "Nome é obrigatório") String nome,

		@NotBlank(message = "Telefone é obrigatório") String telefone,

		@NotBlank(message = "CPF é obrigatório") String cpf) {


    
}