package com.example.Muttley.evento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventoMapper {
    
    @Mapping(target = "criadorId", source = "criador.id")
    EventoResponseDTO toDto(Evento evento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criador", ignore = true)
    @Mapping(target = "apresentadores", ignore = true)
    Evento toEntity(EventoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criador", ignore = true) 
    @Mapping(target = "apresentadores", ignore = true)
    void updateEntityFromDto(EventoRequestDTO dto, @MappingTarget Evento evento);
}