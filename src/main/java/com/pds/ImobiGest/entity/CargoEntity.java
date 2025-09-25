package com.pds.ImobiGest.entity;

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

    @OneToMany(mappedBy = "cargo")
    private List<ProfissionalCargoEntity> profissionais;

    @OneToMany(mappedBy = "cargo")
    private List<ConfigComissaoEntity> configComissao;
}
