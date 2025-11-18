package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.VendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface VendaRepository extends JpaRepository<VendaEntity, Integer> {
    @Query(value = "SELECT COALESCE(SUM(v.valor_total), 0) FROM venda v WHERE v.id_imobiliaria = :idImobiliaria AND EXTRACT(YEAR FROM v.data_venda) = :ano", nativeQuery = true)
    BigDecimal somarVendasPorImobiliariaEAno(@Param("idImobiliaria") Integer idImobiliaria, @Param("ano") int ano);}
