package com.forumhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,
        
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha
) {
}

