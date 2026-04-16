package com.example.Muttley.certificado;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificadoService {

    private final CertificadoRepository repository;
    private final CertificadoMapper mapper;

    @Transactional
    public CertificadoResponseDTO salvar(CertificadoRequestDTO dto) {
        Certificado certificado = mapper.toEntity(dto);
        return mapper.toDto(repository.save(certificado));
    }

    @Transactional
    public CertificadoResponseDTO atualizar(Long id, CertificadoRequestDTO dto) {
        Certificado certificadoExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certificado não encontrado"));
        mapper.updateEntityFromDto(dto, certificadoExistente);
        return mapper.toDto(repository.save(certificadoExistente));
    }

    public List<CertificadoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional
    public void apagar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Certificado não encontrado");
        }
        repository.deleteById(id);
    }
    
    public CertificadoResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Certificado não encontrado"));
    }
}