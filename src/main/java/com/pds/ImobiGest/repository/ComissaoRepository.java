package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.ComissaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComissaoRepository extends JpaRepository<ComissaoEntity, Integer> {
    List<ComissaoEntity> findByVendaId(Integer vendaId);
}