package com.esg.energiasustentavel.controller;

import com.esg.energiasustentavel.model.Alerta;
import com.esg.energiasustentavel.repository.AlertaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@Tag(name = "Alertas", description = "Gestão de alertas de consumo excessivo")
@SecurityRequirement(name = "bearer-key")
public class AlertaController {

    @Autowired
    private AlertaRepository alertaRepository;

    @GetMapping
    @Operation(summary = "Listar todos os alertas", description = "Retorna todos os alertas do sistema")
    public ResponseEntity<List<Alerta>> listarTodos() {
        List<Alerta> alertas = alertaRepository.findAll();
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar alerta por ID", description = "Retorna um alerta específico")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return alertaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Listar alertas por empresa", description = "Retorna todos os alertas de uma empresa")
    public ResponseEntity<List<Alerta>> listarPorEmpresa(@PathVariable Long empresaId) {
        List<Alerta> alertas = alertaRepository.findByEmpresaId(empresaId);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar alertas por status", description = "Retorna alertas filtrados por status (ATIVO, RESOLVIDO)")
    public ResponseEntity<List<Alerta>> listarPorStatus(@PathVariable String status) {
        List<Alerta> alertas = alertaRepository.findByStatus(status);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/severidade/{severidade}")
    @Operation(summary = "Listar alertas por severidade", description = "Retorna alertas filtrados por severidade")
    public ResponseEntity<List<Alerta>> listarPorSeveridade(@PathVariable String severidade) {
        List<Alerta> alertas = alertaRepository.findBySeveridade(severidade);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/empresa/{empresaId}/ativos")
    @Operation(summary = "Listar alertas ativos por empresa", description = "Retorna apenas os alertas ativos de uma empresa")
    public ResponseEntity<List<Alerta>> listarAtivos(@PathVariable Long empresaId) {
        List<Alerta> alertas = alertaRepository.findByEmpresaIdAndStatus(empresaId, "ATIVO");
        return ResponseEntity.ok(alertas);
    }

    @PostMapping
    @Operation(summary = "Criar novo alerta", description = "Registra um novo alerta no sistema")
    public ResponseEntity<Alerta> criar(@Valid @RequestBody Alerta alerta) {
        Alerta novoAlerta = alertaRepository.save(alerta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAlerta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alerta", description = "Atualiza os dados de um alerta existente")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Alerta alertaAtualizado) {
        return alertaRepository.findById(id)
                .map(alerta -> {
                    alerta.setTipo(alertaAtualizado.getTipo());
                    alerta.setSeveridade(alertaAtualizado.getSeveridade());
                    alerta.setMensagem(alertaAtualizado.getMensagem());
                    alerta.setLimiteExcedido(alertaAtualizado.getLimiteExcedido());
                    alerta.setValorAtual(alertaAtualizado.getValorAtual());
                    alerta.setStatus(alertaAtualizado.getStatus());
                    
                    Alerta alertaSalvo = alertaRepository.save(alerta);
                    return ResponseEntity.ok(alertaSalvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/resolver")
    @Operation(summary = "Resolver alerta", description = "Marca um alerta como resolvido")
    public ResponseEntity<?> resolver(@PathVariable Long id) {
        return alertaRepository.findById(id)
                .map(alerta -> {
                    alerta.setStatus("RESOLVIDO");
                    alerta.setDataResolucao(LocalDateTime.now());
                    alertaRepository.save(alerta);
                    return ResponseEntity.ok("Alerta resolvido com sucesso");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir alerta", description = "Remove um alerta do sistema")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return alertaRepository.findById(id)
                .map(alerta -> {
                    alertaRepository.delete(alerta);
                    return ResponseEntity.ok("Alerta excluído com sucesso");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
