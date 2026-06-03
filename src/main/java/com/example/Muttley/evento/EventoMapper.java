package com.example.Muttley.evento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventoMapper {
    
    @Mapping(source = "gestorCriador.id", target = "gestorCriadorId")
    @Mapping(source = "gestorCriador.nome", target = "gestorCriadorNome")
    EventoResponseDTO toDto(Evento evento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apresentadores", ignore = true)
    @Mapping(target = "gestorCriador", ignore = true)
    @Mapping(target = "tokenCheckoutEstatico", ignore = true)
    @Mapping(target = "tokenCheckoutDinamico", ignore = true)
    Evento toEntity(EventoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apresentadores", ignore = true)
    @Mapping(target = "gestorCriador", ignore = true)
    @Mapping(target = "tokenCheckoutEstatico", ignore = true)
    @Mapping(target = "tokenCheckoutDinamico", ignore = true)
    void updateEntityFromDto(EventoRequestDTO dto, @MappingTarget Evento evento);
}