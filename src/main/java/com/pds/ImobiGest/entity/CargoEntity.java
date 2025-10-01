package com.pds.ImobiGest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "CARGO")
@Table(name = "CARGO")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CargoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARGO")
    @SequenceGenerator(name = "SEQ_CARGO", sequenceName = "SEQ_CARGO", allocationSize = 1)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @JsonBackReference("profissional-cargo")
    @OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
    private List<ProfissionalCargoEntity> profissionais;

    @OneToMany(mappedBy = "cargo")
    @JsonIgnoreProperties({"imobiliaria", "cargo"})
    private List<ConfigComissaoEntity> configComissao;
}
