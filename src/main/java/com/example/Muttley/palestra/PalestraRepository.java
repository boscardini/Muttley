package com.example.Muttley.palestra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalestraRepository extends JpaRepository<Palestra, Long> {
}