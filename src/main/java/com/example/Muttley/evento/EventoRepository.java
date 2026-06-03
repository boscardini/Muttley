package com.example.Muttley.evento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByApresentadoresId(Long id);

    boolean existsByGestorCriadorId(Long gestorCriadorId);
}