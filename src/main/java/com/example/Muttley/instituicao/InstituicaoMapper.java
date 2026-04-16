package com.example.Muttley.instituicao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InstituicaoMapper {

    InstituicaoResponseDTO toDto(Instituicao instituicao);

    @Mapping(target = "id", ignore = true)
    Instituicao toEntity(InstituicaoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(InstituicaoRequestDTO dto, @MappingTarget Instituicao instituicao);
}