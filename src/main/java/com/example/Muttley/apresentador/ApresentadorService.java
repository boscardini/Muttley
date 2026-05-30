package com.example.Muttley.apresentador;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Muttley.evento.Evento;
import com.example.Muttley.evento.EventoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApresentadorService {

    private final ApresentadorRepository repository;
    private final ApresentadorMapper mapper;
    private final EventoRepository eventoRepository;

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
        // 1. Encontra todos os eventos onde esse apresentador está
        List<Evento> eventos = eventoRepository.findByApresentadoresId(id);
        // 2. Remove o apresentador de cada um
        for (Evento evento : eventos) {
            evento.getApresentadores().removeIf(a -> a.getId().equals(id));
            eventoRepository.save(evento);
        }
        // 3. Agora pode apagar o apresentador
        repository.deleteById(id);
    }
}
