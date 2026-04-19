package com.esg.energiasustentavel.controller;

import com.esg.energiasustentavel.model.Empresa;
import com.esg.energiasustentavel.repository.EmpresaRepository;
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
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "Gestão de empresas cadastradas")
@SecurityRequirement(name = "bearer-key")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping
    @Operation(summary = "Listar todas as empresas", description = "Retorna todas as empresas cadastradas")
    public ResponseEntity<List<Empresa>> listarTodas() {
        List<Empresa> empresas = empresaRepository.findAll();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empresa por ID", description = "Retorna uma empresa específica pelo ID")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return empresaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova empresa", description = "Cria uma nova empresa no sistema")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Empresa empresa) {
        if (empresaRepository.existsByCnpj(empresa.getCnpj())) {
            return ResponseEntity.badRequest().body("CNPJ já cadastrado");
        }

        Empresa novaEmpresa = empresaRepository.save(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar empresa", description = "Atualiza os dados de uma empresa existente")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Empresa empresaAtualizada) {
        return empresaRepository.findById(id)
                .map(empresa -> {
                    empresa.setNome(empresaAtualizada.getNome());
                    empresa.setSetor(empresaAtualizada.getSetor());
                    empresa.setEndereco(empresaAtualizada.getEndereco());
                    empresa.setCidade(empresaAtualizada.getCidade());
                    empresa.setEstado(empresaAtualizada.getEstado());
                    empresa.setContato(empresaAtualizada.getContato());
                    empresa.setEmail(empresaAtualizada.getEmail());
                    
                    Empresa empresaSalva = empresaRepository.save(empresa);
                    return ResponseEntity.ok(empresaSalva);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir empresa", description = "Remove uma empresa do sistema")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return empresaRepository.findById(id)
                .map(empresa -> {
                    empresaRepository.delete(empresa);
                    return ResponseEntity.ok("Empresa excluída com sucesso");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cnpj/{cnpj}")
    @Operation(summary = "Buscar empresa por CNPJ", description = "Retorna uma empresa específica pelo CNPJ")
    public ResponseEntity<?> buscarPorCnpj(@PathVariable String cnpj) {
        return empresaRepository.findByCnpj(cnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
