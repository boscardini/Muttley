package com.example.Muttley.apresentador;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apresentadores")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ApresentadorController {

    private final ApresentadorService service;

    @PostMapping
    public ResponseEntity<ApresentadorResponseDTO> cadastrar(@RequestBody @Valid ApresentadorRequestDTO dto) {
        ApresentadorResponseDTO response = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApresentadorResponseDTO> editar(@PathVariable Long id, @RequestBody @Valid ApresentadorRequestDTO dto) {
        ApresentadorResponseDTO response = service.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ApresentadorResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApresentadorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}