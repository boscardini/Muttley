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
        return mapper.toDto(repository.save(usuario));
    }

    public UsuarioResponseDTO realizarLogin(String email, String senha) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta");
        }

        if (!usuario.isAprovado()) {
            throw new RuntimeException("Aguarde a aprovação do administrador");
        }

        return mapper.toDto(usuario);
    }

    @Transactional
    public void aprovarUsuario(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setAprovado(true);
        repository.save(usuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional
    public void apagar(Long id) {
        repository.deleteById(id);
    }
}