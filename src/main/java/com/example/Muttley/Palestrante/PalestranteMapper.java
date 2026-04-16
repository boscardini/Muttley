package com.example.Muttley.Palestrante;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel =  "spring")
public interface PalestranteMapper {
    PalestranteResponseDTO toDto(Palestrante palestrante);

    @Mapping(target = "id", ignore = true)
    Palestrante toEntity(PalestranteRequestDTO dto);

<<<<<<< HEAD
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(PalestranteRequestDTO dto, @MappingTarget Palestrante palestrante);
}
=======
	// Entity → DTO
	AtualizacaoPalestrante toDTO(Palestrante palestrante);

	// DTO → Entity (novo)
	@Mapping(target = "id", ignore = true)
	Palestrante toEntity(AtualizacaoPalestrante dto);

	// Atualizar existente
	@Mapping(target = "id", ignore = true)
	void updateEntityFromDto(AtualizacaoPalestrante dto, @MappingTarget Palestrante palestrante);

  
    // 🔹 LISTAGEM
    ListagemPalestrante toListagemDto(Palestrante palestrante);

}

>>>>>>> 603350635508d27648f105525c82b6f4af2eab71
