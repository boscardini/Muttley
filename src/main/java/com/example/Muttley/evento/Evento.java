package com.example.Muttley.evento;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "eventos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;

    private LocalDate dataInicio;
    
    /* * columnDefinition = "TIME"
     * O Java tem o 'LocalTime', mas às vezes o banco de dados tenta salvar isso como 'DATETIME' 
     * ou um número binário estranho. Esse comando força o banco a criar a coluna 
     * exatamente com o tipo 'TIME' do SQL (ex: 14:30:00).
     */
    @Column(columnDefinition = "TIME")
    private LocalTime horaInicio;

    private LocalDate dataFim;

    @Column(columnDefinition = "TIME")
    private LocalTime horaFim;

    /* * @PrePersist
     * Isso é um Gatilho de Ciclo de Vida (Lifecycle Trigger). 
     * Milissegundos ANTES do Hibernate rodar o comando "INSERT" no banco de dados, ele executa essa função.
     * É o lugar perfeito para garantir valores padrão, para que não dê erro de "null" no banco.
     */
    @PrePersist
    protected void onCreate() {
        if (dataInicio == null) dataInicio = LocalDate.now();
        if (horaInicio == null) horaInicio = LocalTime.now();
    }
}