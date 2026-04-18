package com.example.Muttley.participante;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParticipanteMapper {

    ParticipanteResponseDTO toDto(Participante participante);

    @Mapping(target = "id", ignore = true)
    Participante toEntity(ParticipanteRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ParticipanteRequestDTO dto, @MappingTarget Participante participante);
}
