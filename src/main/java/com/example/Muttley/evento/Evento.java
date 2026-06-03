package com.example.Muttley.evento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.Muttley.apresentador.Apresentador;
import com.example.Muttley.usuario.Usuario;

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

    @ManyToMany
    @JoinTable(
        name = "eventos_apresentadores",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "apresentador_id")
    )
    private List<Apresentador> apresentadores = new ArrayList<>();

    private LocalDate dataInicio;
    
    @Column(columnDefinition = "TIME")
    private LocalTime horaInicio;

    private LocalDate dataFim;

    @Column(columnDefinition = "TIME")
    private LocalTime horaFim;

    @Column(nullable = false)
    private Integer complexidade = 0; 
    
    @Column(nullable = false)
    private boolean requerCheckout = true;

    private String tokenCheckoutEstatico;
    
    private String tokenCheckoutDinamico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gestor_criador_id")
    private Usuario gestorCriador;

    @Column(columnDefinition = "TEXT")
    private String assinaturaDescricao;

    @PrePersist
    protected void onCreate() {
        if (dataInicio == null) dataInicio = LocalDate.now();
        if (horaInicio == null) horaInicio = LocalTime.now();
        if (complexidade == null) complexidade = 0;
        
        if (requerCheckout) {
            if (tokenCheckoutEstatico == null) {
                this.tokenCheckoutEstatico = "ESTATIC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            }
            if (tokenCheckoutDinamico == null) {
                this.tokenCheckoutDinamico = UUID.randomUUID().toString().substring(0, 8);
            }
        }
    }
}