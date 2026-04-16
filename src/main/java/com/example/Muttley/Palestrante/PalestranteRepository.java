package com.example.Muttley.Palestrante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalestranteRepository extends JpaRepository<Palestrante, Long>{}
