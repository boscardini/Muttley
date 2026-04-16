package com.example.Muttley.palestra;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PalestraService {

    private final PalestraRepository repository;
    private final PalestraMapper mapper;

    @Transactional
    public PalestraResponseDTO salvar(PalestraRequestDTO dto) {
        Palestra palestra = mapper.toEntity(dto);
        Palestra palestraSalva = repository.save(palestra);
        return mapper.toDto(palestraSalva);
    }

    @Transactional
    public PalestraResponseDTO atualizar(Long id, PalestraRequestDTO dto) {
        Palestra palestraExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Palestra não encontrada"));
        mapper.updateEntityFromDto(dto, palestraExistente);
        
        return mapper.toDto(repository.save(palestraExistente));
    }

    public List<PalestraResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public void apagar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Palestra não encontrada");
        }
        repository.deleteById(id);
    }
    
    public PalestraResponseDTO buscarPorId(Long id) {
        Palestra palestra = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Palestra não encontrada"));
        return mapper.toDto(palestra);
    }
}