package com.example.Muttley.instituicao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AtualizacaoInstituicao(
        Long id,

        @NotBlank(message = "O nome da instituição é obrigatório") 
        String nome,

        @NotBlank(message = "O CNPJ é obrigatório") 
        String cnpj,

        @NotBlank(message = "O e-mail é obrigatório") 
        @Email(message = "E-mail inválido") 
        String email,

        @NotBlank(message = "A senha é obrigatória") 
        String senha,

        @NotBlank(message = "Confirme sua senha") 
        String confirmarSenha
) {
}