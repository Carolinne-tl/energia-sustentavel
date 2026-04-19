package com.esg.energiasustentavel.repository;

import com.esg.energiasustentavel.model.MetaEficiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaEficienciaRepository extends JpaRepository<MetaEficiencia, Long> {
    List<MetaEficiencia> findByEmpresaId(Long empresaId);
    List<MetaEficiencia> findByStatus(String status);
    List<MetaEficiencia> findByEmpresaIdAndStatus(Long empresaId, String status);
}
