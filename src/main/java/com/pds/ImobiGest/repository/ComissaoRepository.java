package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.ComissaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComissaoRepository extends JpaRepository<ComissaoEntity, Integer> {
    List<ComissaoEntity> findByVendaId(Integer vendaId);

    @Query(value = "SELECT COALESCE(SUM(c.valor_comissao), 0) FROM comissao c " +
            "JOIN venda v ON v.id = c.id_venda " +
            "JOIN profissional p ON p.id = c.id_profissional " +
            "WHERE p.id_imobiliaria = :idImobiliaria AND EXTRACT(YEAR FROM v.data_venda) = :ano",
            nativeQuery = true)
    BigDecimal somarComissoesPorImobiliariaEAno(@Param("idImobiliaria") Integer idImobiliaria, @Param("ano") int ano);

    @Query(value = "SELECT COALESCE(SUM(c.valor_comissao), 0) FROM comissao c " +
            "JOIN profissional p ON p.id = c.id_profissional " +
            "JOIN profissional_cargo pc ON pc.id_profissional = p.id " +
            "JOIN cargo ca ON ca.id = pc.id_cargo " +
            "WHERE p.id_imobiliaria = :idImobiliaria AND ca.nome = 'Sócio'", nativeQuery = true)
    BigDecimal somarComissoesSocios(@Param("idImobiliaria") Integer idImobiliaria);

    @Query(value = "SELECT COALESCE(SUM(c.valor_comissao), 0) FROM comissao c " +
            "JOIN profissional p ON p.id = c.id_profissional " +
            "JOIN profissional_cargo pc ON pc.id_profissional = p.id " +
            "JOIN cargo ca ON ca.id = pc.id_cargo " +
            "WHERE p.id_imobiliaria = :idImobiliaria AND ca.nome = 'Corretor'", nativeQuery = true)
    BigDecimal somarComissoesCorretores(@Param("idImobiliaria") Integer idImobiliaria);

    @Query(value = "SELECT COALESCE(SUM(c.valor_comissao), 0) FROM comissao c " +
            "JOIN venda v ON v.id = c.id_venda " +
            "JOIN profissional p ON p.id = c.id_profissional " +
            "WHERE p.id_imobiliaria = :idImobiliaria",
            nativeQuery = true)
    BigDecimal somarTodasComissoesPorImobiliaria(@Param("idImobiliaria") Integer idImobiliaria);

    @Query(value = "SELECT COUNT(DISTINCT DATE_TRUNC('month', v.data_venda)) FROM comissao c " +
            "JOIN venda v ON v.id = c.id_venda " +
            "JOIN profissional p ON p.id = c.id_profissional " +
            "WHERE p.id_imobiliaria = :idImobiliaria",
            nativeQuery = true)
    Integer contarMesesOperacao(@Param("idImobiliaria") Integer idImobiliaria);

    @Query(value = "SELECT COALESCE(SUM(c.valor_comissao), 0) FROM comissao c " +
            "JOIN venda v ON v.id = c.id_venda " +
            "JOIN profissional p ON p.id = c.id_profissional " +
            "WHERE p.id_imobiliaria = :idImobiliaria " +
            "AND v.data_venda BETWEEN :dataInicio AND :dataFim",
            nativeQuery = true)
    BigDecimal somarComissoesPorImobiliariaPeriodo(@Param("idImobiliaria") Integer idImobiliaria,
                                                   @Param("dataInicio") LocalDate dataInicio,
                                                   @Param("dataFim") LocalDate dataFim);

    @Query(value = "SELECT COALESCE(SUM(c.valor_comissao), 0) FROM comissao c " +
            "JOIN venda v ON v.id = c.id_venda " +
            "JOIN profissional p ON p.id = c.id_profissional " +
            "JOIN profissional_cargo pc ON pc.id_profissional = p.id " +
            "JOIN cargo ca ON ca.id = pc.id_cargo " +
            "WHERE p.id_imobiliaria = :idImobiliaria AND ca.nome = 'Sócio' " +
            "AND v.data_venda BETWEEN :dataInicio AND :dataFim",
            nativeQuery = true)
    BigDecimal somarComissoesSociosPeriodo(@Param("idImobiliaria") Integer idImobiliaria,
                                           @Param("dataInicio") LocalDate dataInicio,
                                           @Param("dataFim") LocalDate dataFim);

    @Query(value = "SELECT COALESCE(SUM(c.valor_comissao), 0) FROM comissao c " +
            "JOIN venda v ON v.id = c.id_venda " +
            "JOIN profissional p ON p.id = c.id_profissional " +
            "JOIN profissional_cargo pc ON pc.id_profissional = p.id " +
            "JOIN cargo ca ON ca.id = pc.id_cargo " +
            "WHERE p.id_imobiliaria = :idImobiliaria AND ca.nome = 'Corretor' " +
            "AND v.data_venda BETWEEN :dataInicio AND :dataFim",
            nativeQuery = true)
    BigDecimal somarComissoesCorretoresPeriodo(@Param("idImobiliaria") Integer idImobiliaria,
                                               @Param("dataInicio") LocalDate dataInicio,
                                               @Param("dataFim") LocalDate dataFim);
}