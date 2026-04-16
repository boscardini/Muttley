package com.example.Muttley.palestra;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PalestraMapper {

    PalestraResponseDTO toDto(Palestra palestra);

    @Mapping(target = "id", ignore = true)
    Palestra toEntity(PalestraRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(PalestraRequestDTO dto, @MappingTarget Palestra palestra);
}