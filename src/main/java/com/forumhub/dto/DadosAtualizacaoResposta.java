package com.forumhub.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoResposta(
        @NotNull(message = "ID é obrigatório")
        Long id,
        String mensagem,
        Boolean solucao
) {
}

