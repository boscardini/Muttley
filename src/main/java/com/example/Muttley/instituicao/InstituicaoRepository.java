package com.example.Muttley.instituicao;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
}
=======
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
    Optional<Instituicao> findByEmail(String email);
}
>>>>>>> 603350635508d27648f105525c82b6f4af2eab71
