package com.esg.energiasustentavel.controller;

import com.esg.energiasustentavel.model.RelatorioSustentabilidade;
import com.esg.energiasustentavel.repository.RelatorioSustentabilidadeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios-sustentabilidade")
@Tag(name = "Relatórios de Sustentabilidade", description = "Geração e consulta de relatórios de sustentabilidade")
@SecurityRequirement(name = "bearer-key")
public class RelatorioSustentabilidadeController {

    @Autowired
    private RelatorioSustentabilidadeRepository relatorioRepository;

    @GetMapping
    @Operation(summary = "Listar todos os relatórios", description = "Retorna todos os relatórios de sustentabilidade")
    public ResponseEntity<List<RelatorioSustentabilidade>> listarTodos() {
        List<RelatorioSustentabilidade> relatorios = relatorioRepository.findAll();
        return ResponseEntity.ok(relatorios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar relatório por ID", description = "Retorna um relatório específico")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return relatorioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Listar relatórios por empresa", description = "Retorna todos os relatórios de uma empresa")
    public ResponseEntity<List<RelatorioSustentabilidade>> listarPorEmpresa(@PathVariable Long empresaId) {
        List<RelatorioSustentabilidade> relatorios = relatorioRepository.findByEmpresaIdOrderByDataGeracaoDesc(empresaId);
        return ResponseEntity.ok(relatorios);
    }

    @PostMapping
    @Operation(summary = "Gerar novo relatório", description = "Cria um novo relatório de sustentabilidade")
    public ResponseEntity<RelatorioSustentabilidade> gerar(@Valid @RequestBody RelatorioSustentabilidade relatorio) {
        // Calcula a redução de CO2 baseada no consumo (fator: 0.475 kg CO2/kWh)
        if (relatorio.getEconomiaKwh() != null) {
            relatorio.setReducaoCo2Kg(relatorio.getEconomiaKwh() * 0.475);
        }
        
        RelatorioSustentabilidade novoRelatorio = relatorioRepository.save(relatorio);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRelatorio);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar relatório", description = "Atualiza os dados de um relatório existente")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody RelatorioSustentabilidade relatorioAtualizado) {
        return relatorioRepository.findById(id)
                .map(relatorio -> {
                    relatorio.setPeriodo(relatorioAtualizado.getPeriodo());
                    relatorio.setConsumoTotalKwh(relatorioAtualizado.getConsumoTotalKwh());
                    relatorio.setCustoTotal(relatorioAtualizado.getCustoTotal());
                    relatorio.setEconomiaKwh(relatorioAtualizado.getEconomiaKwh());
                    relatorio.setReducaoCo2Kg(relatorioAtualizado.getReducaoCo2Kg());
                    relatorio.setEficienciaPercentual(relatorioAtualizado.getEficienciaPercentual());
                    relatorio.setObservacoes(relatorioAtualizado.getObservacoes());
                    
                    RelatorioSustentabilidade relatorioSalvo = relatorioRepository.save(relatorio);
                    return ResponseEntity.ok(relatorioSalvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir relatório", description = "Remove um relatório do sistema")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return relatorioRepository.findById(id)
                .map(relatorio -> {
                    relatorioRepository.delete(relatorio);
                    return ResponseEntity.ok("Relatório excluído com sucesso");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
