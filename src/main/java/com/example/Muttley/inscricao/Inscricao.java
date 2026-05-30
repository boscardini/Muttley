package com.example.Muttley.inscricao;

import com.example.Muttley.evento.Evento;
import com.example.Muttley.participante.Participante;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscricoes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento: Várias inscrições pertencem a UM participante
    @ManyToOne(optional = false)
    @JoinColumn(name = "participante_id", nullable = false)
    private Participante participante;

    // Relacionamento: Várias inscrições pertencem a UM evento
    @ManyToOne(optional = false)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    // Começa sempre como INSCRITO
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusInscricao status = StatusInscricao.INSCRITO;

    // Marcadores de tempo para o fluxo dos QR Codes
    private LocalDateTime dataHoraCheckIn;
    private LocalDateTime dataHoraCheckOut;

    // Histórico para o front-end mostrar: "Você ganhou +26 XP nesta palestra"
    @Column(nullable = false)
    private Integer pontosRecebidos = 0;
}