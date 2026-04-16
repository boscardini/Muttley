package com.example.Muttley.certificado;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CertificadoMapper {

    CertificadoResponseDTO toDto(Certificado certificado);

    @Mapping(target = "id", ignore = true)
    Certificado toEntity(CertificadoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CertificadoRequestDTO dto, @MappingTarget Certificado certificado);
}