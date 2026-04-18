package com.example.Muttley.evento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventoMapper {
    
    EventoResponseDTO toDto(Evento evento);

    @Mapping(target = "id", ignore = true)
    Evento toEntity(EventoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(EventoRequestDTO dto, @MappingTarget Evento evento);
}