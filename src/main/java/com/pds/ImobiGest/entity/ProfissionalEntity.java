package com.pds.ImobiGest.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JoinColumn(name = "id_imobiliaria", nullable = false)
    @JsonBackReference
    private ImobiliariaEntity imobiliaria;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"profissional"})
    private List<ComissaoEntity> comissoes;


    @OneToMany(mappedBy = "profissional", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"imobiliaria", "comissoes", "cargos"})
    private List<ProfissionalCargoEntity> cargos;

}
