package com.example.Muttley.palestra;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "palestras")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Palestra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    private String titulo;
    private String descricao;
    private List<String>habilidades;
    private String palestrante;
    private LocalDate dataInicio = LocalDate.now();
    @Column(columnDefinition = "TIME")
    private LocalTime horaInicio = LocalTime.now();
    private LocalDate dataFim = LocalDate.now();
    @Column(columnDefinition = "TIME")
    private LocalTime horaFim = LocalTime.now();
    
    @Column(unique = true)
    private String codigo; 
}