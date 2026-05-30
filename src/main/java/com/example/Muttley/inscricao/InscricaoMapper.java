package com.example.Muttley.inscricao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InscricaoMapper {

    @Mapping(source = "participante.id", target = "participanteId")
    @Mapping(source = "participante.nome", target = "nomeParticipante")
    @Mapping(source = "evento.id", target = "eventoId")
    @Mapping(source = "evento.titulo", target = "tituloEvento")
    InscricaoResponseDTO toDto(Inscricao inscricao);
    
}