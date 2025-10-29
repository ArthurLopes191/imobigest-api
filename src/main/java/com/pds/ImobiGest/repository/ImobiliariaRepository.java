package com.pds.ImobiGest.repository;

import com.pds.ImobiGest.entity.ImobiliariaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImobiliariaRepository extends JpaRepository<ImobiliariaEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM comissao WHERE id_profissional IN 
        (SELECT id FROM profissional WHERE id_imobiliaria = ?1)
        """, nativeQuery = true)
    void deleteComissoesByImobiliariaId(Integer imobiliariaId);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM profissional_cargo WHERE id_profissional IN 
        (SELECT id FROM profissional WHERE id_imobiliaria = ?1)
        """, nativeQuery = true)
    void deleteProfissionalCargosByImobiliariaId(Integer imobiliariaId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM config_comissao WHERE id_imobiliaria = ?1", nativeQuery = true)
    void deleteConfigComissaoByImobiliariaId(Integer imobiliariaId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM profissional WHERE id_imobiliaria = ?1", nativeQuery = true)
    void deleteProfissionaisByImobiliariaId(Integer imobiliariaId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM venda WHERE id_imobiliaria = ?1", nativeQuery = true)
    void deleteVendasByImobiliariaId(Integer imobiliariaId);
}