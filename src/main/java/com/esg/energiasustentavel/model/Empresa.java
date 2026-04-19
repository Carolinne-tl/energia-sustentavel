package com.esg.energiasustentavel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "empresas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    @Column(nullable = false, length = 200)
    private String nome;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX")
    @Column(unique = true, nullable = false, length = 18)
    private String cnpj;

    @Size(max = 100, message = "Setor deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String setor;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    @Column(length = 255)
    private String endereco;

    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String cidade;

    @Size(max = 2, message = "Estado deve ter 2 caracteres")
    @Column(length = 2)
    private String estado;

    @Size(max = 100, message = "Contato deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String contato;

    @Email(message = "Email deve ser válido")
    @Column(length = 100)
    private String email;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }
}
