package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.ConfigComissaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigComissaoRepository extends JpaRepository<ConfigComissaoEntity, Integer> {

    Optional<ConfigComissaoEntity> findByImobiliariaIdAndCargoId(Integer idImobiliaria, Integer idCargo);
}
