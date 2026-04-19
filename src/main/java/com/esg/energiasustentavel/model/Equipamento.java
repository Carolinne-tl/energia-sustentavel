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
@Table(name = "equipamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Empresa é obrigatória")
    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    @Column(nullable = false, length = 200)
    private String nome;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 100, message = "Tipo deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String tipo;

    @NotNull(message = "Potência é obrigatória")
    @Positive(message = "Potência deve ser positiva")
    @Column(name = "potencia_watts", nullable = false)
    private Double potenciaWatts;

    @Size(max = 200, message = "Localização deve ter no máximo 200 caracteres")
    @Column(length = 200)
    private String localizacao;

    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String status = "ATIVO";

    @Column(name = "data_instalacao")
    private LocalDate dataInstalacao;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }
}
