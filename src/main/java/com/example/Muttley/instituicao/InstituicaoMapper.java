package com.example.Muttley.instituicao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InstituicaoMapper {

<<<<<<< HEAD
    InstituicaoResponseDTO toDto(Instituicao instituicao);

    @Mapping(target = "id", ignore = true)
    Instituicao toEntity(InstituicaoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(InstituicaoRequestDTO dto, @MappingTarget Instituicao instituicao);
=======
    // Converte a Entidade Instituicao para o DTO de Atualização (usado para carregar o formulário)
    AtualizacaoInstituicao toAtualizacaoDto(Instituicao instituicao);

    // Converte o DTO para a Entidade Instituicao (usado na criação de um novo registro)
    @Mapping(target = "id", ignore = true)
    Instituicao toEntityFromAtualizacao(AtualizacaoInstituicao dto);

    // Atualiza uma instância de Instituicao que já existe com os dados vindos do formulário
    @Mapping(target = "id", ignore = true) // Segurança: impede a alteração do ID original
    void updateEntityFromDto(AtualizacaoInstituicao dto, @MappingTarget Instituicao instituicao);
>>>>>>> 603350635508d27648f105525c82b6f4af2eab71
}