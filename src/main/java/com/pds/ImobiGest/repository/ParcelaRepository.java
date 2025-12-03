package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.ParcelaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ParcelaRepository extends JpaRepository<ParcelaEntity, Integer> {
    List<ParcelaEntity> findByVendaId(Integer vendaId);

    @Modifying
    @Transactional
    void deleteByVendaId(Integer vendaId);

    @Query("SELECT p FROM ParcelaEntity p WHERE p.venda.id IN :vendaIds AND (:status IS NULL OR p.status = :status)")
    List<ParcelaEntity> findByVendaIdsAndStatus(@Param("vendaIds") List<Integer> vendaIds, @Param("status") String status);
}
