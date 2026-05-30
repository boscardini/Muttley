package com.example.Muttley.evento;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Muttley.apresentador.Apresentador;
import com.example.Muttley.apresentador.ApresentadorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository repository;
    private final EventoMapper mapper;
    private final ApresentadorRepository apresentadorRepository;

    @Transactional
    public EventoResponseDTO salvar(EventoRequestDTO dto) {
        Evento evento = mapper.toEntity(dto);

        if (dto.apresentadoresIds() != null && !dto.apresentadoresIds().isEmpty()) {
            List<Apresentador> listaApresentadores = apresentadorRepository.findAllById(dto.apresentadoresIds());
            evento.setApresentadores(listaApresentadores);
        }

        return mapper.toDto(repository.save(evento));
    }

    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto) {
        Evento existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));
        
        if (dto.apresentadoresIds() != null && !dto.apresentadoresIds().isEmpty()) {
            List<Apresentador> listaApresentadores = apresentadorRepository.findAllById(dto.apresentadoresIds());
            existente.setApresentadores(listaApresentadores);
        }
        
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

    @Transactional
    public String atualizarTokenCheckoutDinamico(Long eventoId) {
        Evento evento = repository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));
        
        String novoToken = java.util.UUID.randomUUID().toString().substring(0, 8);
        evento.setTokenCheckoutDinamico(novoToken);
        repository.save(evento);
        
        return novoToken;
    }
}