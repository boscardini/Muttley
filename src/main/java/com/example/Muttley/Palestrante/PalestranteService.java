package com.example.Muttley.Palestrante;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PalestranteService {

    @Autowired
    private PalestranteRepository repository;

    @Autowired
    private PalestranteMapper mapper;

    // 🔹 SALVAR OU ATUALIZAR
    public Palestrante salvarOuAtualizar(AtualizacaoPalestrante dto) {

        if (dto.id() != null) {
            // 🔄 Atualização
            Palestrante existente = repository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Palestrante não encontrado com ID: " + dto.id()));

            mapper.updateEntityFromDto(dto, existente);
            return repository.save(existente);

        } else {
            // 🆕 Criação
            Palestrante novo = mapper.toEntity(dto);
            return repository.save(novo);
        }
    }

    // 🔹 LISTAGEM (Entity)
    public List<Palestrante> procurarTodos() {
        return repository.findAll(Sort.by("nome").ascending());
    }

    // 🔹 LISTAGEM (DTO) ⭐ recomendado
    public List<ListagemPalestrante> listarTodos() {
        return repository.findAll(Sort.by("nome").ascending())
                .stream()
                .map(mapper::toListagemDto)
                .toList();
    }

    // 🔹 BUSCAR POR ID
    public Optional<Palestrante> procurarPorId(Long id) {
        return repository.findById(id);
    }

    // 🔹 DELETAR
    public void apagarPorId(Long id) {
        repository.deleteById(id);
    }
}