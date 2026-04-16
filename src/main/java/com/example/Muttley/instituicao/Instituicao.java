package com.example.Muttley.instituicao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instituicoes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @Column(unique = true)
    private String cnpj;
    
    private String endereco;
}