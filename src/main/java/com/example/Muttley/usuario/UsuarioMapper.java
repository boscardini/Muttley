package com.example.Muttley.usuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponseDTO toDto(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aprovado", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aprovado", ignore = true)
    void updateEntityFromDto(UsuarioRequestDTO dto, @MappingTarget Usuario usuario);
}