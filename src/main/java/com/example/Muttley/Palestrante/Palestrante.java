package com.example.Muttley.Palestrante;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD
=======
import jakarta.persistence.*;
>>>>>>> 603350635508d27648f105525c82b6f4af2eab71

@Entity
@Table(name = "palestrantes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Palestrante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
<<<<<<< HEAD
    @Column(unique = true)
    private String cpf;

}
=======
    
    // Deixe apenas este campo 'cpf'. Remova o 'CPF' maiúsculo se existir.
    private String cpf;


    
    public void atualizarInformacoes(AtualizacaoPalestrante dados) {
        if (dados.nome() != null)
            this.nome = dados.nome();

        if (dados.telefone() != null)
            this.telefone = dados.telefone();

        if (dados.cpf() != null)

            this.cpf = dados.cpf();
        }
    }
>>>>>>> 603350635508d27648f105525c82b6f4af2eab71
