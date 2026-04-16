package com.example.Muttley.aluno;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;
    private final AlunoMapper mapper;

    @Transactional
    public AlunoResponseDTO salvar(AlunoRequestDTO dto) {
        Aluno aluno = mapper.toEntity(dto);
        Aluno alunoSalvo = repository.save(aluno);
        return mapper.toDto(alunoSalvo);
    }

    @Transactional
    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO dto) {
        Aluno alunoExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        mapper.updateEntityFromDto(dto, alunoExistente);
        
        return mapper.toDto(repository.save(alunoExistente));
    }

    public List<AlunoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public void apagar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Aluno não encontrado");
        }
        repository.deleteById(id);
    }
    
    public AlunoResponseDTO buscarPorId(Long id) {
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        return mapper.toDto(aluno);
    }
}