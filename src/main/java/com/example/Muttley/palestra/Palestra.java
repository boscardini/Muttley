package com.example.Muttley.palestra;

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
    private String palestrante;
    
    @Column(unique = true)
    private String codigo; 
}