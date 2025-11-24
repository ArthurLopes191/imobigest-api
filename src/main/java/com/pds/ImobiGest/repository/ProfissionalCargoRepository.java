package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.ProfissionalCargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalCargoRepository extends JpaRepository<ProfissionalCargoEntity, Integer> {

    Optional<ProfissionalCargoEntity> findByProfissionalIdAndCargoId(Integer idProfissional, Integer idCargo);

    List<ProfissionalCargoEntity> findByProfissionalId(Integer idProfissional);

    @Query("SELECT pc FROM PROFISSIONAL_CARGO pc " +
            "WHERE pc.cargo.comissaoAutomatica = true " +
            "AND pc.profissional.imobiliaria.id = :idImobiliaria")
    List<ProfissionalCargoEntity> findByCargoComissaoAutomaticaAndImobiliaria(@Param("idImobiliaria") Integer idImobiliaria);
}
