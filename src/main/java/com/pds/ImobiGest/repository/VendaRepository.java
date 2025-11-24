package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.VendaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface VendaRepository extends JpaRepository<VendaEntity, Integer> {
    @Query(value = "SELECT COALESCE(SUM(v.valor_total), 0) FROM venda v WHERE v.id_imobiliaria = :idImobiliaria AND EXTRACT(YEAR FROM v.data_venda) = :ano", nativeQuery = true)
    BigDecimal somarVendasPorImobiliariaEAno(@Param("idImobiliaria") Integer idImobiliaria, @Param("ano") int ano);

    @Query(value = "SELECT v.id, v.descricao_imovel, v.valor_total, v.data_venda, " +
            "v.forma_pagamento, v.qtd_parcelas, v.comprador_nome, v.comprador_contato, v.id_imobiliaria " +
            "FROM venda v " +
            "LEFT JOIN imobiliaria i ON i.id = v.id_imobiliaria " +
            "LEFT JOIN comissao c ON c.id_venda = v.id " +
            "WHERE (:descricao IS NULL OR LOWER(v.descricao_imovel::TEXT) LIKE LOWER(CONCAT('%', :descricao, '%'))) " +
            "AND (:valorMin IS NULL OR v.valor_total >= :valorMin) " +
            "AND (:valorMax IS NULL OR v.valor_total <= :valorMax) " +
            "AND (:dataInicio IS NULL OR DATE(v.data_venda) >= :dataInicio) " +
            "AND (:dataFim IS NULL OR DATE(v.data_venda) <= :dataFim) " +
            "AND (:formaPagamento IS NULL OR v.forma_pagamento = :formaPagamento) " +
            "AND (:idImobiliaria IS NULL OR i.id = :idImobiliaria) " +
            "AND (:idProfissional IS NULL OR c.id_profissional = :idProfissional) " +
            "AND (:nomeComprador IS NULL OR LOWER(v.comprador_nome::TEXT) LIKE LOWER(CONCAT('%', :nomeComprador, '%'))) " +
            "GROUP BY v.id, v.descricao_imovel, v.valor_total, v.data_venda, v.forma_pagamento, v.qtd_parcelas, v.comprador_nome, v.comprador_contato, v.id_imobiliaria " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'valorTotal' AND :sortOrder = 'ASC' THEN v.valor_total END ASC, " +
            "CASE WHEN :sortBy = 'valorTotal' AND :sortOrder = 'DESC' THEN v.valor_total END DESC, " +
            "CASE WHEN :sortBy = 'descricaoImovel' AND :sortOrder = 'ASC' THEN v.descricao_imovel END ASC, " +
            "CASE WHEN :sortBy = 'descricaoImovel' AND :sortOrder = 'DESC' THEN v.descricao_imovel END DESC, " +
            "CASE WHEN (:sortBy = 'dataVenda' OR :sortBy IS NULL) AND :sortOrder = 'ASC' THEN v.data_venda END ASC, " +
            "CASE WHEN (:sortBy = 'dataVenda' OR :sortBy IS NULL) AND :sortOrder = 'DESC' THEN v.data_venda END DESC",
            nativeQuery = true)
    Page<VendaEntity> findVendasWithFilters(
            @Param("descricao") String descricao,
            @Param("valorMin") BigDecimal valorMin,
            @Param("valorMax") BigDecimal valorMax,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("formaPagamento") String formaPagamento,
            @Param("idImobiliaria") Integer idImobiliaria,
            @Param("idProfissional") Integer idProfissional,
            @Param("nomeComprador") String nomeComprador,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            Pageable pageable
    );
}

