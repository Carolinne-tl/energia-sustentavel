package com.esg.energiasustentavel.repository;

import com.esg.energiasustentavel.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByEmpresaId(Long empresaId);
    List<Alerta> findByStatus(String status);
    List<Alerta> findByEmpresaIdAndStatus(Long empresaId, String status);
    List<Alerta> findBySeveridade(String severidade);
}
