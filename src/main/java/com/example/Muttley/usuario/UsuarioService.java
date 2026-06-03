package com.example.Muttley.usuario;

import com.example.Muttley.evento.EventoRepository;
import com.example.Muttley.infra.RegraDeNegocioException;

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
    private final EventoRepository eventoRepository;

    @Transactional
    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {
        if (dto.assinaturaBase64() == null || dto.assinaturaBase64().isBlank()) {
            throw new RegraDeNegocioException("A assinatura é obrigatória para o cadastro de gestor.");
        }
        Usuario usuario = mapper.toEntity(dto);
        usuario.setTipo(TipoUsuario.GESTOR);
        usuario.setAprovado(false);
        usuario.setAssinaturaBase64(dto.assinaturaBase64().trim());
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
        if (eventoRepository.existsByGestorCriadorId(id)) {
            throw new RegraDeNegocioException(
                    "Não é possível excluir o gestor, pois existem eventos criados por ele.");
        }
        repository.deleteById(id);
    }
}