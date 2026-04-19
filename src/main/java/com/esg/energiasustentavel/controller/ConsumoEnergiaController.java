package com.esg.energiasustentavel.controller;

import com.esg.energiasustentavel.model.ConsumoEnergia;
import com.esg.energiasustentavel.repository.ConsumoEnergiaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consumo-energia")
@Tag(name = "Consumo de Energia", description = "Monitoramento e registro de consumo energético")
@SecurityRequirement(name = "bearer-key")
public class ConsumoEnergiaController {

    @Autowired
    private ConsumoEnergiaRepository consumoEnergiaRepository;

    @GetMapping
    @Operation(summary = "Listar todos os registros de consumo", description = "Retorna todos os registros de consumo de energia")
    public ResponseEntity<List<ConsumoEnergia>> listarTodos() {
        List<ConsumoEnergia> consumos = consumoEnergiaRepository.findAll();
        return ResponseEntity.ok(consumos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar consumo por ID", description = "Retorna um registro específico de consumo")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return consumoEnergiaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Listar consumo por empresa", description = "Retorna o histórico de consumo de uma empresa")
    public ResponseEntity<List<ConsumoEnergia>> listarPorEmpresa(@PathVariable Long empresaId) {
        List<ConsumoEnergia> consumos = consumoEnergiaRepository.findByEmpresaId(empresaId);
        return ResponseEntity.ok(consumos);
    }

    @GetMapping("/equipamento/{equipamentoId}")
    @Operation(summary = "Listar consumo por equipamento", description = "Retorna o histórico de consumo de um equipamento")
    public ResponseEntity<List<ConsumoEnergia>> listarPorEquipamento(@PathVariable Long equipamentoId) {
        List<ConsumoEnergia> consumos = consumoEnergiaRepository.findByEquipamentoId(equipamentoId);
        return ResponseEntity.ok(consumos);
    }

    @GetMapping("/empresa/{empresaId}/periodo")
    @Operation(summary = "Consultar consumo por período", description = "Retorna o consumo de uma empresa em um período específico")
    public ResponseEntity<List<ConsumoEnergia>> consultarPorPeriodo(
            @PathVariable Long empresaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        
        List<ConsumoEnergia> consumos = consumoEnergiaRepository.findByEmpresaIdAndPeriodo(empresaId, dataInicio, dataFim);
        return ResponseEntity.ok(consumos);
    }

    @GetMapping("/empresa/{empresaId}/total")
    @Operation(summary = "Calcular consumo total", description = "Retorna o consumo total de energia de uma empresa")
    public ResponseEntity<Map<String, Object>> calcularConsumoTotal(@PathVariable Long empresaId) {
        Double consumoTotal = consumoEnergiaRepository.calcularConsumoTotalPorEmpresa(empresaId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("empresaId", empresaId);
        response.put("consumoTotalKwh", consumoTotal != null ? consumoTotal : 0.0);
        response.put("unidade", "kWh");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar novo consumo", description = "Registra uma nova leitura de consumo de energia")
    public ResponseEntity<ConsumoEnergia> registrar(@Valid @RequestBody ConsumoEnergia consumo) {
        ConsumoEnergia novoConsumo = consumoEnergiaRepository.save(consumo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoConsumo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro de consumo", description = "Atualiza um registro de consumo existente")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody ConsumoEnergia consumoAtualizado) {
        return consumoEnergiaRepository.findById(id)
                .map(consumo -> {
                    consumo.setDataLeitura(consumoAtualizado.getDataLeitura());
                    consumo.setConsumoKwh(consumoAtualizado.getConsumoKwh());
                    consumo.setCustoEstimado(consumoAtualizado.getCustoEstimado());
                    consumo.setPeriodo(consumoAtualizado.getPeriodo());
                    consumo.setObservacoes(consumoAtualizado.getObservacoes());
                    
                    ConsumoEnergia consumoSalvo = consumoEnergiaRepository.save(consumo);
                    return ResponseEntity.ok(consumoSalvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro de consumo", description = "Remove um registro de consumo do sistema")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return consumoEnergiaRepository.findById(id)
                .map(consumo -> {
                    consumoEnergiaRepository.delete(consumo);
                    return ResponseEntity.ok("Registro de consumo excluído com sucesso");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
