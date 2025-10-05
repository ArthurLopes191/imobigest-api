package com.pds.ImobiGest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "CONFIG_COMISSAO")
@Table(name = "CONFIG_COMISSAO")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfigComissaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONFIG_COMISSAO")
    @SequenceGenerator(name = "SEQ_CONFIG_COMISSAO", sequenceName = "SEQ_CONFIG_COMISSAO", allocationSize = 1)
    private Integer id;

    @Column(name = "percentual")
    private BigDecimal percentual;

    @ManyToOne
    @JoinColumn(name = "id_imobiliaria", referencedColumnName = "id")
    @JsonIgnoreProperties("configuracoes")
    private ImobiliariaEntity imobiliaria;

    @ManyToOne
    @JoinColumn(name= "id_cargo", referencedColumnName = "id")
    @JsonIgnoreProperties("configuracoes")
    private CargoEntity cargo;
}
