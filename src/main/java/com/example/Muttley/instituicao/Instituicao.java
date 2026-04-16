package com.example.Muttley.instituicao;

<<<<<<< HEAD
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instituicoes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> 603350635508d27648f105525c82b6f4af2eab71
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
<<<<<<< HEAD
    
    @Column(unique = true)
    private String cnpj;
    
    private String endereco;
=======
    private String cnpj;
    private String email;
    private String senha;
>>>>>>> 603350635508d27648f105525c82b6f4af2eab71
}