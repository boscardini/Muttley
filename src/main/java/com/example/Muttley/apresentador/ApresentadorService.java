package com.example.Muttley.apresentador;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApresentadorService {

    private final ApresentadorRepository repository;
    private final ApresentadorMapper mapper;

    @Transactional
    public ApresentadorResponseDTO salvar(ApresentadorRequestDTO dto) {
        Apresentador novoApresentador = mapper.toEntity(dto);
         return mapper.toDto(repository.save(novoApresentador));
    }

    @Transactional
    public ApresentadorResponseDTO atualizar(Long id, ApresentadorRequestDTO dto) {
        Apresentador apresentadorExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apresentador não encontrado"));
        
        mapper.updateEntityFromDto(dto, apresentadorExistente);
        
        return mapper.toDto(repository.save(apresentadorExistente));
    }

    public List<ApresentadorResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public ApresentadorResponseDTO buscarPorId(Long id) {
        Apresentador apresentador = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apresentador não encontrado"));
        return mapper.toDto(apresentador);
    }

    @Transactional
    public void apagar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Apresentador não encontrado");
        }
        repository.deleteById(id);
    }
}
