package com.example.Muttley.aluno;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alunos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aluno {
<<<<<<< HEAD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String curso;
    @Column(unique = true)
    private String email;
    private String linkedin;
    
}
=======

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aluno_id")
	private long id;
	private String nome;
	private String curso;
	private String email;
	private String senha;
	private String linkedin;
	// te

}
>>>>>>> 603350635508d27648f105525c82b6f4af2eab71
