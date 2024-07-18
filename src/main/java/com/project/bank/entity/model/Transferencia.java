package com.project.bank.entity.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transferencias")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "remetente_id", nullable = false)
    private Conta remetente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Conta destinatario;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "data_transferencia", nullable = false)
    private LocalDateTime dataTransferencia;
}
