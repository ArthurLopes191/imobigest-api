package com.pds.ImobiGest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "IMOBILIARIA")
@Table(name = "IMOBILIARIA")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImobiliariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IMOBILIARIA")
    @SequenceGenerator(name = "SEQ_IMOBILIARIA", sequenceName = "SEQ_IMOBILIARIA", allocationSize = 1)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "meta")
    private BigDecimal meta;

    @OneToMany(mappedBy = "imobiliaria", cascade = CascadeType.ALL)
    private List<ProfissionalEntity> profissionais;

    @OneToMany(mappedBy = "imobiliaria")
    @JsonIgnoreProperties({"imobiliaria", "cargo"})
    private List<ConfigComissaoEntity> configuracoes;

}
