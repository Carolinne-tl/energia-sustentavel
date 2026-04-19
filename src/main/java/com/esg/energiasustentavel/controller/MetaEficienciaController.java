package com.esg.energiasustentavel.controller;

import com.esg.energiasustentavel.model.MetaEficiencia;
import com.esg.energiasustentavel.repository.MetaEficienciaRepository;
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
@RequestMapping("/api/metas-eficiencia")
@Tag(name = "Metas de Eficiência", description = "Gestão de metas de eficiência energética")
@SecurityRequirement(name = "bearer-key")
public class MetaEficienciaController {

    @Autowired
    private MetaEficienciaRepository metaEficienciaRepository;

    @GetMapping
    @Operation(summary = "Listar todas as metas", description = "Retorna todas as metas de eficiência cadastradas")
    public ResponseEntity<List<MetaEficiencia>> listarTodas() {
        List<MetaEficiencia> metas = metaEficienciaRepository.findAll();
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar meta por ID", description = "Retorna uma meta específica")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return metaEficienciaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Listar metas por empresa", description = "Retorna todas as metas de uma empresa")
    public ResponseEntity<List<MetaEficiencia>> listarPorEmpresa(@PathVariable Long empresaId) {
        List<MetaEficiencia> metas = metaEficienciaRepository.findByEmpresaId(empresaId);
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar metas por status", description = "Retorna metas filtradas por status")
    public ResponseEntity<List<MetaEficiencia>> listarPorStatus(@PathVariable String status) {
        List<MetaEficiencia> metas = metaEficienciaRepository.findByStatus(status);
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/empresa/{empresaId}/em-andamento")
    @Operation(summary = "Listar metas em andamento", description = "Retorna as metas em andamento de uma empresa")
    public ResponseEntity<List<MetaEficiencia>> listarEmAndamento(@PathVariable Long empresaId) {
        List<MetaEficiencia> metas = metaEficienciaRepository.findByEmpresaIdAndStatus(empresaId, "EM_ANDAMENTO");
        return ResponseEntity.ok(metas);
    }

    @PostMapping
    @Operation(summary = "Criar nova meta", description = "Cadastra uma nova meta de eficiência energética")
    public ResponseEntity<MetaEficiencia> criar(@Valid @RequestBody MetaEficiencia meta) {
        MetaEficiencia novaMeta = metaEficienciaRepository.save(meta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMeta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar meta", description = "Atualiza os dados de uma meta existente")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody MetaEficiencia metaAtualizada) {
        return metaEficienciaRepository.findById(id)
                .map(meta -> {
                    meta.setDescricao(metaAtualizada.getDescricao());
                    meta.setTipoMeta(metaAtualizada.getTipoMeta());
                    meta.setValorAlvo(metaAtualizada.getValorAlvo());
                    meta.setValorAtual(metaAtualizada.getValorAtual());
                    meta.setUnidade(metaAtualizada.getUnidade());
                    meta.setDataInicio(metaAtualizada.getDataInicio());
                    meta.setDataFim(metaAtualizada.getDataFim());
                    meta.setStatus(metaAtualizada.getStatus());
                    
                    MetaEficiencia metaSalva = metaEficienciaRepository.save(meta);
                    return ResponseEntity.ok(metaSalva);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/progresso")
    @Operation(summary = "Atualizar progresso da meta", description = "Atualiza o valor atual de progresso da meta")
    public ResponseEntity<?> atualizarProgresso(@PathVariable Long id, @RequestParam Double valorAtual) {
        return metaEficienciaRepository.findById(id)
                .map(meta -> {
                    meta.setValorAtual(valorAtual);
                    
                    // Verifica se a meta foi atingida
                    if (valorAtual >= meta.getValorAlvo()) {
                        meta.setStatus("CONCLUIDA");
                    }
                    
                    metaEficienciaRepository.save(meta);
                    return ResponseEntity.ok("Progresso atualizado com sucesso");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir meta", description = "Remove uma meta do sistema")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return metaEficienciaRepository.findById(id)
                .map(meta -> {
                    metaEficienciaRepository.delete(meta);
                    return ResponseEntity.ok("Meta excluída com sucesso");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
