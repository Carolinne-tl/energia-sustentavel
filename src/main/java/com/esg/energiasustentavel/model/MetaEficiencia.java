package com.esg.energiasustentavel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "metas_eficiencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaEficiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Empresa é obrigatória")
    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descricao;

    @NotBlank(message = "Tipo de meta é obrigatório")
    @Size(max = 50, message = "Tipo de meta deve ter no máximo 50 caracteres")
    @Column(name = "tipo_meta", nullable = false, length = 50)
    private String tipoMeta;

    @NotNull(message = "Valor alvo é obrigatório")
    @Positive(message = "Valor alvo deve ser positivo")
    @Column(name = "valor_alvo", nullable = false)
    private Double valorAlvo;

    @Column(name = "valor_atual")
    private Double valorAtual = 0.0;

    @Size(max = 20, message = "Unidade deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String unidade;

    @NotNull(message = "Data de início é obrigatória")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull(message = "Data de fim é obrigatória")
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String status = "EM_ANDAMENTO";

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}
