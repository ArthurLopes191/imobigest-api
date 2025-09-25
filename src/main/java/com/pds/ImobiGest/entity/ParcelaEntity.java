package com.pds.ImobiGest.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pds.ImobiGest.enums.StatusParcela;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "PARCELA")
@Table(name = "PARCELA")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParcelaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARCELA")
    @SequenceGenerator(name = "SEQ_PARCELA", sequenceName = "SEQ_PARCELA", allocationSize = 1)
    private Integer id;

    @Column(name = "numero_parcela")
    private Integer numeroParcela;

    @Column(name = "valor_parcela")
    private BigDecimal valorParcela;

    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;

    @Enumerated(EnumType.STRING)
    private StatusParcela status;

    @ManyToOne
    @JoinColumn(name = "id_venda", referencedColumnName = "id")
    @JsonBackReference
    private VendaEntity venda;




}
