package com.example.Muttley.certificado;

import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/certificados")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CertificadoController {

    private final CertificadoService service;

    @PostMapping
    public ResponseEntity<CertificadoResponseDTO> cadastrar(@RequestBody @Valid CertificadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificadoResponseDTO> editar(@PathVariable Long id, @RequestBody @Valid CertificadoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<CertificadoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificadoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}