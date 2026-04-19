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
@Table(name = "alertas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Empresa é obrigatória")
    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @Column(name = "equipamento_id")
    private Long equipamentoId;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 50, message = "Tipo deve ter no máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String tipo;

    @NotBlank(message = "Severidade é obrigatória")
    @Size(max = 20, message = "Severidade deve ter no máximo 20 caracteres")
    @Column(nullable = false, length = 20)
    private String severidade;

    @NotBlank(message = "Mensagem é obrigatória")
    @Size(max = 500, message = "Mensagem deve ter no máximo 500 caracteres")
    @Column(nullable = false, length = 500)
    private String mensagem;

    @Column(name = "limite_excedido")
    private Double limiteExcedido;

    @Column(name = "valor_atual")
    private Double valorAtual;

    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String status = "ATIVO";

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_resolucao")
    private LocalDateTime dataResolucao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}
