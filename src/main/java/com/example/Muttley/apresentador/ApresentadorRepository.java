package com.example.Muttley.apresentador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApresentadorRepository extends JpaRepository<Apresentador, Long>{}
