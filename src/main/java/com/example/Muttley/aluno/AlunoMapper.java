package com.example.Muttley.aluno;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

	// Converte Entity para DTO (para preencher o formulário de edição no Eclipse)
	AtualizacaoAluno toAtualizacaoDto(Aluno aluno);

	// Converte DTO para Entity (Criação de novo aluno - ignora o ID para o banco
	// gerar)
	@Mapping(target = "id", ignore = true)
	Aluno toEntityFromAtualizacao(AtualizacaoAluno dto);

	// Atualiza a Entity que já existe com os novos dados do formulário
	@Mapping(target = "id", ignore = true) // Segurança: nunca permite mudar o ID via formulário
	void updateEntityFromDto(AtualizacaoAluno dto, @MappingTarget Aluno aluno);
}