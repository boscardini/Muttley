package com.example.Muttley.participante;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/participantes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ParticipanteController {

    private final ParticipanteService service;

    @PostMapping
    public ResponseEntity<ParticipanteResponseDTO> cadastrar(@RequestBody @Valid ParticipanteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}") 
    public ResponseEntity<ParticipanteResponseDTO> editar(@PathVariable Long id, @RequestBody @Valid ParticipanteRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<ParticipanteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}