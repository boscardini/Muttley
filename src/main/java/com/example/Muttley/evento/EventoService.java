package com.example.Muttley.evento;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Muttley.apresentador.Apresentador;
import com.example.Muttley.apresentador.ApresentadorRepository;
import com.example.Muttley.inscricao.InscricaoRepository;
import com.example.Muttley.infra.RegraDeNegocioException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository repository;
    private final EventoMapper mapper;
    private final ApresentadorRepository apresentadorRepository;
    private final InscricaoRepository inscricaoRepository;

    private EventoResponseDTO enriquecerComInscritos(Evento evento) {
        EventoResponseDTO dto = mapper.toDto(evento);
        long total = inscricaoRepository.countByEventoId(evento.getId());
        return new EventoResponseDTO(
            dto.id(), dto.titulo(), dto.descricao(), dto.dataInicio(), dto.horaInicio(),
            dto.dataFim(), dto.horaFim(), dto.complexidade(), dto.requerCheckout(),
            dto.tokenCheckoutEstatico(), dto.tokenCheckoutDinamico(), dto.apresentadores(),
            total
        );
    }

    @Transactional
    public EventoResponseDTO salvar(EventoRequestDTO dto) {
        Evento evento = mapper.toEntity(dto);

        if (dto.apresentadoresIds() != null && !dto.apresentadoresIds().isEmpty()) {
            List<Apresentador> listaApresentadores = apresentadorRepository.findAllById(dto.apresentadoresIds());
            evento.setApresentadores(listaApresentadores);
        }

        return enriquecerComInscritos(repository.save(evento));
    }

    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto, String role) {
        if (!"ADMIN".equals(role) && !"GESTOR".equals(role)) {
            throw new RegraDeNegocioException("Acesso negado: Você não possui permissão para alterar eventos.");
        }

        Evento existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));

        mapper.updateEntityFromDto(dto, existente);

        if (dto.apresentadoresIds() != null && !dto.apresentadoresIds().isEmpty()) {
            List<Apresentador> listaApresentadores = apresentadorRepository.findAllById(dto.apresentadoresIds());
            existente.setApresentadores(listaApresentadores);
        } else {
            if (existente.getApresentadores() != null) {
                existente.getApresentadores().clear();
            }
        }
        
        return enriquecerComInscritos(repository.save(existente));
    }

    public List<EventoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::enriquecerComInscritos)
                .toList();
    }

    public EventoResponseDTO buscarPorId(Long id) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));
        return enriquecerComInscritos(evento);
    }

    @Transactional
    public void apagar(Long id, String role) {
        if (!"ADMIN".equals(role) && !"GESTOR".equals(role)) {
            throw new RegraDeNegocioException("Acesso negado: Você não possui permissão para excluir eventos.");
        }

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Evento não encontrado");
        }

        inscricaoRepository.deleteByEventoId(id);
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