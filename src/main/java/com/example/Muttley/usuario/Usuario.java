package com.example.Muttley.usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil;

    @Column(nullable = false)
    private boolean aprovado = false; 

    @PrePersist
    public void prePersist() {
        if (this.perfil == Perfil.ADMIN) {
            this.aprovado = true;
        } else {
            this.aprovado = false;
        }
    }
}

enum Perfil {
    ADMIN,
    GESTOR
}