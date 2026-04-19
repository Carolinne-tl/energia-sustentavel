package com.esg.energiasustentavel.repository;

import com.esg.energiasustentavel.model.RelatorioSustentabilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioSustentabilidadeRepository extends JpaRepository<RelatorioSustentabilidade, Long> {
    List<RelatorioSustentabilidade> findByEmpresaId(Long empresaId);
    List<RelatorioSustentabilidade> findByEmpresaIdOrderByDataGeracaoDesc(Long empresaId);
}
