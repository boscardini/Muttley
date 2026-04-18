package com.example.Muttley.evento;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository repository;
    private final EventoMapper mapper;

    @Transactional
    public EventoResponseDTO salvar(EventoRequestDTO dto) {
        Evento evento = mapper.toEntity(dto);
        return mapper.toDto(repository.save(evento));
    }

    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto) {
        Evento existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));
        
        mapper.updateEntityFromDto(dto, existente);
        return mapper.toDto(repository.save(existente));
    }

    public List<EventoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public EventoResponseDTO buscarPorId(Long id) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));
        return mapper.toDto(evento);
    }

    @Transactional
    public void apagar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Evento não encontrado");
        }
        repository.deleteById(id);
    }
}