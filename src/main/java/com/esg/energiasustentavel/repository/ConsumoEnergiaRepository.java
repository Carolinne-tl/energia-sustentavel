package com.esg.energiasustentavel.repository;

import com.esg.energiasustentavel.model.ConsumoEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsumoEnergiaRepository extends JpaRepository<ConsumoEnergia, Long> {
    List<ConsumoEnergia> findByEmpresaId(Long empresaId);
    List<ConsumoEnergia> findByEquipamentoId(Long equipamentoId);
    
    @Query("SELECT c FROM ConsumoEnergia c WHERE c.empresaId = :empresaId AND c.dataLeitura BETWEEN :dataInicio AND :dataFim ORDER BY c.dataLeitura DESC")
    List<ConsumoEnergia> findByEmpresaIdAndPeriodo(
        @Param("empresaId") Long empresaId, 
        @Param("dataInicio") LocalDateTime dataInicio, 
        @Param("dataFim") LocalDateTime dataFim
    );
    
    @Query("SELECT SUM(c.consumoKwh) FROM ConsumoEnergia c WHERE c.empresaId = :empresaId")
    Double calcularConsumoTotalPorEmpresa(@Param("empresaId") Long empresaId);
}
