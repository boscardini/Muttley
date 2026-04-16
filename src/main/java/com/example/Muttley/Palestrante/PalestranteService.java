package com.example.Muttley.Palestrante;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PalestranteService {

    private final PalestranteRepository repository;
    private final PalestranteMapper mapper;

    @Transactional
    public PalestranteResponseDTO salvar(PalestranteRequestDTO dto) {
        Palestrante novoPalestrante = mapper.toEntity(dto);
         return mapper.toDto(repository.save(novoPalestrante));
    }

    @Transactional
    public PalestranteResponseDTO atualizar(Long id, PalestranteRequestDTO dto) {
        Palestrante palestranteExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Palestrante não encontrado"));
        
        mapper.updateEntityFromDto(dto, palestranteExistente);
        
        return mapper.toDto(repository.save(palestranteExistente));
    }

    public List<PalestranteResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public PalestranteResponseDTO buscarPorId(Long id) {
        Palestrante palestrante = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Palestrante não encontrado"));
        return mapper.toDto(palestrante);
    }

    @Transactional
    public void apagar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Palestrante não encontrado");
        }
        repository.deleteById(id);
    }
}
