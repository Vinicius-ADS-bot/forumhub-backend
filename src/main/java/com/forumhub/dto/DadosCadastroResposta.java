package com.forumhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroResposta(
        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem,
        
        @NotNull(message = "ID do tópico é obrigatório")
        Long topicoId,
        
        @NotNull(message = "ID do autor é obrigatório")
        Long autorId
) {
}

