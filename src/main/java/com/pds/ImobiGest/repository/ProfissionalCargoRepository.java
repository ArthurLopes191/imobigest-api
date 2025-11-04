package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.ProfissionalCargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalCargoRepository extends JpaRepository<ProfissionalCargoEntity, Integer> {

    Optional<ProfissionalCargoEntity> findByProfissionalIdAndCargoId(Integer idProfissional, Integer idCargo);

    List<ProfissionalCargoEntity> findByProfissionalId(Integer idProfissional);
}
