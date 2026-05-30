package com.example.Muttley.evento;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Muttley.apresentador.Apresentador;
import com.example.Muttley.apresentador.ApresentadorRepository;
import com.example.Muttley.usuario.Usuario;
import com.example.Muttley.usuario.UsuarioRepository;
import com.example.Muttley.inscricao.InscricaoRepository; // <-- Importação adicionada
import com.example.Muttley.infra.RegraDeNegocioException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository repository;
    private final EventoMapper mapper;
    private final ApresentadorRepository apresentadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final InscricaoRepository inscricaoRepository; // <-- Injeção adicionada

    @Transactional
    public EventoResponseDTO salvar(EventoRequestDTO dto, Long usuarioId) {
        Evento evento = mapper.toEntity(dto);

        Usuario criador = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário criador não encontrado."));
        evento.setCriador(criador);

        if (dto.apresentadoresIds() != null && !dto.apresentadoresIds().isEmpty()) {
            List<Apresentador> listaApresentadores = apresentadorRepository.findAllById(dto.apresentadoresIds());
            evento.setApresentadores(listaApresentadores);
        }

        return mapper.toDto(repository.save(evento));
    }

    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto, Long usuarioId, String role) {
        // Garante que o usuário logado possui nível gerencial (ADMIN ou GESTOR)
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
            existente.setApresentadores(List.of()); 
        }
        
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
    public void apagar(Long id, Long usuarioId, String role) {
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