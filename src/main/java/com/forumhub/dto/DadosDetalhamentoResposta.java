package com.forumhub.dto;

import com.forumhub.model.Resposta;
import java.time.LocalDateTime;

public record DadosDetalhamentoResposta(
        Long id,
        String mensagem,
        LocalDateTime dataCriacao,
        Boolean solucao,
        Long topicoId,
        String topicoTitulo,
        Long autorId,
        String autorNome
) {
    public DadosDetalhamentoResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getDataCriacao(), resposta.getSolucao(),
             resposta.getTopico().getId(), resposta.getTopico().getTitulo(),
             resposta.getAutor().getId(), resposta.getAutor().getNome());
    }
}

