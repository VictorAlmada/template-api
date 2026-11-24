package br.com.victoralmada.template_api.controller;

import br.com.victoralmada.template_api.dto.request.PessoaRequest;
import br.com.victoralmada.template_api.dto.response.PessoaResponse;
import br.com.victoralmada.template_api.service.PessoaService;
import br.com.victoralmada.template_api.service.impl.PessoaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> listarTodos() {
        return ResponseEntity.ok(pessoaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pessoaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> salvar(@Valid @RequestBody PessoaRequest request) {
        PessoaResponse response = pessoaService.salvar(request);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody PessoaRequest request) {
        return ResponseEntity.ok(pessoaService.atualizar(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        pessoaService.excluir(id);
        return ResponseEntity.noContent().build();
    }




}
