package com.esg.energiasustentavel.repository;

import com.esg.energiasustentavel.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    List<Equipamento> findByEmpresaId(Long empresaId);
    List<Equipamento> findByStatus(String status);
    List<Equipamento> findByEmpresaIdAndStatus(Long empresaId, String status);
}
