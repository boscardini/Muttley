package com.example.Muttley.participante;

import com.example.Muttley.infra.RegraDeNegocioException;
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
        if (repository.existsByCpf(dto.cpf())) {
            throw new RegraDeNegocioException("Este CPF já está cadastrado no sistema.");
        }
        if (repository.existsByEmail(dto.email())) {
            throw new RegraDeNegocioException("Este E-mail já está em uso por outro participante.");
        }

        Participante participante = mapper.toEntity(dto);
        participante.setPontosTotais(0); // Todo aluno começa com 0 XP
        return mapper.toDto(repository.save(participante));
    }

    @Transactional
    public ParticipanteResponseDTO atualizar(Long id, ParticipanteRequestDTO dto) {
        Participante existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Participante não encontrado"));
        
        // Verifica se ele tentou mudar para um email/cpf de outra pessoa
        if (!existente.getCpf().equals(dto.cpf()) && repository.existsByCpf(dto.cpf())) {
            throw new RegraDeNegocioException("Este CPF já está sendo utilizado.");
        }
        if (!existente.getEmail().equals(dto.email()) && repository.existsByEmail(dto.email())) {
            throw new RegraDeNegocioException("Este E-mail já está sendo utilizado.");
        }

        mapper.updateEntityFromDto(dto, existente);
        return mapper.toDto(repository.save(existente));
    }

    public List<ParticipanteResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public ParticipanteResponseDTO buscarPorId(Long id) {
        Participante participante = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Participante não encontrado"));
        return mapper.toDto(participante);
    }

    @Transactional
    public void apagar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Participante não encontrado");
        }
        repository.deleteById(id);
    }

    public ParticipanteResponseDTO realizarLogin(ParticipanteLoginDTO dto) {
    Participante participante = repository.findByCpf(dto.cpf())
            .orElseThrow(() -> new RegraDeNegocioException("CPF ou Data de Nascimento inválidos."));

    if (!participante.getDataNascimento().equals(dto.dataNascimento())) {
        throw new RegraDeNegocioException("CPF ou Data de Nascimento inválidos.");
    }

        return mapper.toDto(participante);
    }
}