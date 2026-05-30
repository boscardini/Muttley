package com.example.Muttley.inscricao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    
    // Evita inscrições duplicadas
    boolean existsByParticipanteIdAndEventoId(Long participanteId, Long eventoId);
    
    // Busca a inscrição específica para dar o Check-in ou Check-out
    Optional<Inscricao> findByParticipanteIdAndEventoId(Long participanteId, Long eventoId);
    List<Inscricao> findAllByParticipanteCpfOrderByDataHoraCheckInDesc(String cpf);

    @Modifying
    @Query("DELETE FROM Inscricao i WHERE i.evento.id = :eventoId")
    void deleteByEventoId(@Param("eventoId") Long eventoId);
}