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
    //private String sigla;
    private String cnpj;
//    private String cep;
//    private String cidade;
//    private String emailInstitucional;
//    private String senha;
    private String endereco;

}