package com.pds.ImobiGest.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imobiliaria_id", nullable = false)
    private ImobiliariaEntity imobiliaria;

    @JsonManagedReference
    @OneToMany(mappedBy = "profissional")
    private List<ComissaoEntity> comissoes;

    @JsonIgnore
    @OneToMany(mappedBy = "profissional")
    private List<ProfissionalCargoEntity> cargos;

}
