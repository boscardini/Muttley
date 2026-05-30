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
            @RequestAttribute("usuarioId") Long usuarioId) {
        // Passa o ID de quem está logado para ser gravado como "criador"
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> editar(
            @PathVariable Long id, 
            @RequestBody @Valid EventoRequestDTO dto,
            @RequestAttribute("usuarioId") Long usuarioId,
            @RequestAttribute("usuarioRole") String role) {
        // Passa o ID e a Role para o Service validar se ele é o dono do evento ou ADMIN
        return ResponseEntity.ok(service.atualizar(id, dto, usuarioId, role));
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listar() {
        // Rota pública, não exige leitura de token no parâmetro
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long id) {
        // Rota pública, não exige leitura de token no parâmetro
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            @RequestAttribute("usuarioId") Long usuarioId,
            @RequestAttribute("usuarioRole") String role) {
        // Passa o ID e a Role para o Service travar a exclusão indevida
        service.apagar(id, usuarioId, role);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/girar-token")
    public ResponseEntity<Map<String, String>> girarToken(@PathVariable Long id) {
        String novoToken = service.atualizarTokenCheckoutDinamico(id);
        return ResponseEntity.ok(Map.of("token", novoToken));
    }
}