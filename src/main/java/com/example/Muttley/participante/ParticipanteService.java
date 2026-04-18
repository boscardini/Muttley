package com.example.Muttley.participante;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

    private final ParticipanteRepository repository;
    private final ParticipanteMapper mapper;

    @Transactional
    public ParticipanteResponseDTO salvar(ParticipanteRequestDTO dto) {
        Participante participante = mapper.toEntity(dto);
        Participante participanteSalvo = repository.save(participante);
        return mapper.toDto(participanteSalvo);
    }

    @Transactional
    public ParticipanteResponseDTO atualizar(Long id, ParticipanteRequestDTO dto) {
        Participante participanteExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Participante não encontrado"));
        mapper.updateEntityFromDto(dto, participanteExistente);
        
        return mapper.toDto(repository.save(participanteExistente));
    }

    public List<ParticipanteResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public void apagar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Participante não encontrado");
        }
        repository.deleteById(id);
    }
    
    public ParticipanteResponseDTO buscarPorId(Long id) {
        Participante participante = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Participante não encontrado"));
        return mapper.toDto(participante);
    }
}