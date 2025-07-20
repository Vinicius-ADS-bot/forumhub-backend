package com.forumhub.controller;

import com.forumhub.model.Curso;
import com.forumhub.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid Curso curso, UriComponentsBuilder uriBuilder) {
        repository.save(curso);
        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(curso);
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        var cursos = repository.findAll();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var curso = repository.getReferenceById(id);
        return ResponseEntity.ok(curso);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid Curso dados) {
        var curso = repository.getReferenceById(dados.getId());
        curso.setNome(dados.getNome());
        curso.setCategoria(dados.getCategoria());
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

