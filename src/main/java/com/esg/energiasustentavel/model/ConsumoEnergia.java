package com.esg.energiasustentavel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "consumo_energia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Empresa é obrigatória")
    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @Column(name = "equipamento_id")
    private Long equipamentoId;

    @NotNull(message = "Data de leitura é obrigatória")
    @Column(name = "data_leitura", nullable = false)
    private LocalDateTime dataLeitura;

    @NotNull(message = "Consumo é obrigatório")
    @Positive(message = "Consumo deve ser positivo")
    @Column(name = "consumo_kwh", nullable = false)
    private Double consumoKwh;

    @Column(name = "custo_estimado")
    private Double custoEstimado;

    @Size(max = 20, message = "Período deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String periodo;

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    @Column(length = 500)
    private String observacoes;

    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    @PrePersist
    protected void onCreate() {
        dataRegistro = LocalDateTime.now();
    }
}
