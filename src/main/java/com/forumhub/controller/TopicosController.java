package com.forumhub.controller;

import com.forumhub.dto.DadosAtualizacaoTopico;
import com.forumhub.dto.DadosCadastroTopico;
import com.forumhub.dto.DadosDetalhamentoTopico;
import com.forumhub.dto.DadosListagemTopico;
import com.forumhub.model.Topico;
import com.forumhub.repository.TopicoRepository;
import com.forumhub.repository.UsuarioRepository;
import com.forumhub.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        var autor = usuarioRepository.getReferenceById(dados.autorId());
        var curso = cursoRepository.getReferenceById(dados.cursoId());
        var topico = new Topico(dados.titulo(), dados.mensagem(), autor, curso);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {
        var page = topicoRepository.findAllByStatusTrue(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topico = topicoRepository.getReferenceById(dados.id());
        topico.atualizarInformacoes(dados.titulo(), dados.mensagem(), dados.status());
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        topico.excluir();
        return ResponseEntity.noContent().build();
    }
}

