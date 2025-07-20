package com.forumhub.controller;

import com.forumhub.dto.DadosCadastroResposta;
import com.forumhub.dto.DadosDetalhamentoResposta;
import com.forumhub.dto.DadosAtualizacaoResposta;
import com.forumhub.model.Resposta;
import com.forumhub.repository.RespostaRepository;
import com.forumhub.repository.TopicoRepository;
import com.forumhub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroResposta dados, UriComponentsBuilder uriBuilder) {
        var topico = topicoRepository.getReferenceById(dados.topicoId());
        var autor = usuarioRepository.getReferenceById(dados.autorId());
        var resposta = new Resposta(dados.mensagem(), topico, autor);
        respostaRepository.save(resposta);
        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoResposta>> listar() {
        var respostas = respostaRepository.findAll().stream()
                .map(DadosDetalhamentoResposta::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respostas);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var resposta = respostaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoResposta dados) {
        var resposta = respostaRepository.getReferenceById(dados.id());
        resposta.setMensagem(dados.mensagem());
        if (dados.solucao() != null) {
            resposta.setSolucao(dados.solucao());
        }
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        respostaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

