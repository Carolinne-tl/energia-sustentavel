package com.esg.energiasustentavel.controller;

import com.esg.energiasustentavel.model.Equipamento;
import com.esg.energiasustentavel.repository.EquipamentoRepository;
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
@RequestMapping("/api/equipamentos")
@Tag(name = "Equipamentos", description = "Gestão de equipamentos e sensores IoT")
@SecurityRequirement(name = "bearer-key")
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @GetMapping
    @Operation(summary = "Listar todos os equipamentos", description = "Retorna todos os equipamentos cadastrados")
    public ResponseEntity<List<Equipamento>> listarTodos() {
        List<Equipamento> equipamentos = equipamentoRepository.findAll();
        return ResponseEntity.ok(equipamentos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar equipamento por ID", description = "Retorna um equipamento específico pelo ID")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return equipamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Listar equipamentos por empresa", description = "Retorna todos os equipamentos de uma empresa")
    public ResponseEntity<List<Equipamento>> listarPorEmpresa(@PathVariable Long empresaId) {
        List<Equipamento> equipamentos = equipamentoRepository.findByEmpresaId(empresaId);
        return ResponseEntity.ok(equipamentos);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar equipamentos por status", description = "Retorna equipamentos filtrados por status")
    public ResponseEntity<List<Equipamento>> listarPorStatus(@PathVariable String status) {
        List<Equipamento> equipamentos = equipamentoRepository.findByStatus(status);
        return ResponseEntity.ok(equipamentos);
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo equipamento", description = "Registra um novo equipamento no sistema")
    public ResponseEntity<Equipamento> cadastrar(@Valid @RequestBody Equipamento equipamento) {
        Equipamento novoEquipamento = equipamentoRepository.save(equipamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEquipamento);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar equipamento", description = "Atualiza os dados de um equipamento existente")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Equipamento equipamentoAtualizado) {
        return equipamentoRepository.findById(id)
                .map(equipamento -> {
                    equipamento.setNome(equipamentoAtualizado.getNome());
                    equipamento.setTipo(equipamentoAtualizado.getTipo());
                    equipamento.setPotenciaWatts(equipamentoAtualizado.getPotenciaWatts());
                    equipamento.setLocalizacao(equipamentoAtualizado.getLocalizacao());
                    equipamento.setStatus(equipamentoAtualizado.getStatus());
                    equipamento.setDataInstalacao(equipamentoAtualizado.getDataInstalacao());
                    
                    Equipamento equipamentoSalvo = equipamentoRepository.save(equipamento);
                    return ResponseEntity.ok(equipamentoSalvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir equipamento", description = "Remove um equipamento do sistema")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return equipamentoRepository.findById(id)
                .map(equipamento -> {
                    equipamentoRepository.delete(equipamento);
                    return ResponseEntity.ok("Equipamento excluído com sucesso");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/desligar")
    @Operation(summary = "Desligar equipamento ocioso", description = "Altera o status do equipamento para INATIVO")
    public ResponseEntity<?> desligar(@PathVariable Long id) {
        return equipamentoRepository.findById(id)
                .map(equipamento -> {
                    equipamento.setStatus("INATIVO");
                    equipamentoRepository.save(equipamento);
                    return ResponseEntity.ok("Equipamento desligado automaticamente");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
