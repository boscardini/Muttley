package com.example.Muttley.palestra;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/palestras")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PalestraController {

    private final PalestraService service;

    @PostMapping
    public ResponseEntity<PalestraResponseDTO> cadastrar(@RequestBody @Valid PalestraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PalestraResponseDTO> editar(@PathVariable Long id, @RequestBody @Valid PalestraRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<PalestraResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PalestraResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}