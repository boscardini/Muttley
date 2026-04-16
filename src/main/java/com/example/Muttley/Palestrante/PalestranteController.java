package com.example.Muttley.Palestrante;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/palestrantes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PalestranteController {

    private final PalestranteService service;

    @PostMapping
    public ResponseEntity<PalestranteResponseDTO> cadastrar(@RequestBody @Valid PalestranteRequestDTO dto) {
        PalestranteResponseDTO response = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PalestranteResponseDTO> editar(@PathVariable Long id, @RequestBody @Valid PalestranteRequestDTO dto) {
        PalestranteResponseDTO response = service.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PalestranteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PalestranteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
