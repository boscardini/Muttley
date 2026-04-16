package com.example.Muttley.aluno;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;

public record AtualizacaoAluno(Long id,

		@NotBlank(message = "Nome é obrigatório") String nome,

		@NotBlank(message = "Curso é obrigatório") String curso,

		@NotBlank(message = "Email é obrigatório") @jakarta.validation.constraints.Email(message = "Email inválido") String email,

		@NotBlank(message = "Senha é obrigatória") String senha,

		@NotBlank(message = "Confirme sua senha") String confirmarSenha,

		@NotBlank(message = "LinkedIn é obrigatório") String linkedin) {
}