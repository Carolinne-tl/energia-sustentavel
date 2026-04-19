package com.esg.energiasustentavel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "relatorios_sustentabilidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioSustentabilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Empresa é obrigatória")
    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @NotBlank(message = "Período é obrigatório")
    @Size(max = 50, message = "Período deve ter no máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String periodo;

    @Column(name = "consumo_total_kwh")
    private Double consumoTotalKwh;

    @Column(name = "custo_total")
    private Double custoTotal;

    @Column(name = "economia_kwh")
    private Double economiaKwh;

    @Column(name = "reducao_co2_kg")
    private Double reducaoCo2Kg;

    @Column(name = "eficiencia_percentual")
    private Double eficienciaPercentual;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String observacoes;

    @Column(name = "data_geracao")
    private LocalDateTime dataGeracao;

    @PrePersist
    protected void onCreate() {
        dataGeracao = LocalDateTime.now();
    }
}
