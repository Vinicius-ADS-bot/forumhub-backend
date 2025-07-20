package com.forumhub.dto;

import com.forumhub.model.Topico;
import com.forumhub.model.Usuario;
import com.forumhub.model.Curso;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Boolean status,
        String autorNome,
        String cursoNome
) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), 
             topico.getDataCriacao(), topico.getStatus(), topico.getAutor().getNome(), topico.getCurso().getNome());
    }
}

