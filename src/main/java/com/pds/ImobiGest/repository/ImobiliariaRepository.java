package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.ImobiliariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImobiliariaRepository extends JpaRepository<ImobiliariaEntity, Integer> {
}
