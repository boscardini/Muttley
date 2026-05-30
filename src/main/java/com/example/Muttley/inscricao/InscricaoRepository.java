package com.example.Muttley.inscricao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    
    // Evita inscrições duplicadas
    boolean existsByParticipanteIdAndEventoId(Long participanteId, Long eventoId);
    
    // Busca a inscrição específica para dar o Check-in ou Check-out
    Optional<Inscricao> findByParticipanteIdAndEventoId(Long participanteId, Long eventoId);

    List<Inscricao> findAllByParticipanteCpfOrderByDataHoraCheckInDesc(String cpf);
}