package com.pds.ImobiGest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "PROFISSIONAL")
@Table(name = "PROFISSIONAL")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfissionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFISSIONAL")
    @SequenceGenerator(name = "SEQ_PROFISSIONAL", sequenceName = "SEQ_PROFISSIONAL", allocationSize = 1)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "profissional")
    private List<ComissaoEntity> comissoes;

    @OneToMany(mappedBy = "profissional")
    private List<ProfissionalCargoEntity> cargos;

}
