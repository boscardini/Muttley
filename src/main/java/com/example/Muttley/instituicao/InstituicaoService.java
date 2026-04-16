package com.example.Muttley.instituicao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstituicaoService {

    private final InstituicaoRepository repository;
    private final InstituicaoMapper mapper;

    @Transactional
    public InstituicaoResponseDTO salvar(InstituicaoRequestDTO dto) {
        Instituicao instituicao = mapper.toEntity(dto);
        Instituicao instituicaoSalva = repository.save(instituicao);
        return mapper.toDto(instituicaoSalva);
    }

    @Transactional
    public InstituicaoResponseDTO atualizar(Long id, InstituicaoRequestDTO dto) {
        Instituicao instituicaoExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada"));
        mapper.updateEntityFromDto(dto, instituicaoExistente);
        
        return mapper.toDto(repository.save(instituicaoExistente));
    }

    public List<InstituicaoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public void apagar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Instituição não encontrada");
        }
        repository.deleteById(id);
    }
    
    public InstituicaoResponseDTO buscarPorId(Long id) {
        Instituicao instituicao = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada"));
        return mapper.toDto(instituicao);
    }
}