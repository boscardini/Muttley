package com.example.Muttley.aluno;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    AlunoResponseDTO toDto(Aluno aluno);

    @Mapping(target = "id", ignore = true)
    Aluno toEntity(AlunoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(AlunoRequestDTO dto, @MappingTarget Aluno aluno);
}
