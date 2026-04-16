package com.example.Muttley.aluno;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alunos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String curso;
    @Column(unique = true)
    private String email;
    private String linkedin;
    
}
