package com.example.Muttley.apresentador;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel =  "spring")
public interface ApresentadorMapper {
    ApresentadorResponseDTO toDto(Apresentador apresentador);

    @Mapping(target = "id", ignore = true)
    Apresentador toEntity(ApresentadorRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ApresentadorRequestDTO dto, @MappingTarget Apresentador apresentador);
}
