package com.example.Muttley.evento;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService service;

    @PostMapping
    public ResponseEntity<EventoResponseDTO> cadastrar(
            @RequestBody @Valid EventoRequestDTO dto,
            @RequestAttribute(value = "usuarioId", required = false) Long usuarioId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> editar(
            @PathVariable Long id, 
            @RequestBody @Valid EventoRequestDTO dto,
            @RequestAttribute("usuarioRole") String role) {
        return ResponseEntity.ok(service.atualizar(id, dto, role));
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            @RequestAttribute("usuarioRole") String role) {
        service.apagar(id, role);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/girar-token")
    public ResponseEntity<Map<String, String>> girarToken(@PathVariable Long id) {
        String novoToken = service.atualizarTokenCheckoutDinamico(id);
        return ResponseEntity.ok(Map.of("token", novoToken));
    }
}