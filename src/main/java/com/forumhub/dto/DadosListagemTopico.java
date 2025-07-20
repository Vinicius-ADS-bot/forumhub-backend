package com.forumhub.dto;

import com.forumhub.model.Topico;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String autorNome,
        String cursoNome
) {
    public DadosListagemTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), 
             topico.getDataCriacao(), topico.getAutor().getNome(), topico.getCurso().getNome());
    }
}

