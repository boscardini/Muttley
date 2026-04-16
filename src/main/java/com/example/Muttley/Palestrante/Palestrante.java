package com.example.Muttley.Palestrante;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "palestrante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Palestrante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "palestrante_id")
	private Long id;


	private String nome;
	private String telefone;
	private String CPF;

    private String nome;
    private String telefone;
    private String cpf;


	// Atualização manual (igual ao Caminhao
	public void atualizarInformacoes(AtualizacaoPalestrante dados) {
		if (dados.nome() != null)
			this.nome = dados.nome();

		if (dados.telefone() != null)
			this.telefone = dados.telefone();


		if (dados.CPF() != null)
			this.CPF = dados.CPF();
	}

        if (dados.cpf() != null)
            this.cpf = dados.cpf();
    }

}