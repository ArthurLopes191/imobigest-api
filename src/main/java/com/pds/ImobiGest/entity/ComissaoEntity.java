package com.pds.ImobiGest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "COMISSAO")
@Table(name = "COMISSAO")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ComissaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMISSAO")
    @SequenceGenerator(name = "SEQ_COMISSAO", sequenceName = "SEQ_COMISSAO", allocationSize = 1)
    private Integer id;

    @Column(name = "percentual")
    private BigDecimal percentual;

    @Column(name = "valor_comissao")
    private BigDecimal valorComissao;

    @Column(name = "tipo_comissao")
    private String tipoComissao; // "AUTOMATICA" ou "MANUAL"

    @ManyToOne
    @JoinColumn(name = "id_venda")
    private VendaEntity venda;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_profissional")
    private ProfissionalEntity profissional;
}
