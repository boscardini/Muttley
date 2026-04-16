package com.example.Muttley.aluno;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	Optional<Aluno> findByEmail(String email);
}
