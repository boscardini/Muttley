package com.example.Muttley.usuario;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    @Transactional
    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        usuario.setTipo(TipoUsuario.GESTOR);
        usuario.setAprovado(false); 
        return mapper.toDto(repository.save(usuario));
    }

    @Transactional
    public void aprovarUsuario(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setAprovado(true);
        repository.save(usuario);
    }

    public List<UsuarioResponseDTO> listarGestores() {
        return repository.findByTipo(TipoUsuario.GESTOR)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public void apagar(Long id) {
        repository.deleteById(id);
    }
}