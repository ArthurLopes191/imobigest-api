package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.ParcelaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelaRepository extends JpaRepository<ParcelaEntity, Integer> {
}
