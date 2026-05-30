package com.example.Muttley.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByTipo(TipoUsuario tipo);
    List<Usuario> findByTipo(TipoUsuario tipo);
}