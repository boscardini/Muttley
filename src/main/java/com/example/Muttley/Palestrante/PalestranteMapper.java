package com.example.Muttley.Palestrante;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel =  "spring")
public interface PalestranteMapper {
    PalestranteResponseDTO toDto(Palestrante palestrante);

    @Mapping(target = "id", ignore = true)
    Palestrante toEntity(PalestranteRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(PalestranteRequestDTO dto, @MappingTarget Palestrante palestrante);
}
