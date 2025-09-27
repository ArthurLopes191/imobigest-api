package com.pds.ImobiGest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pds.ImobiGest.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "VENDA")
@Table(name = "VENDA")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VENDA")
    @SequenceGenerator(name = "SEQ_VENDA", sequenceName = "SEQ_VENDA", allocationSize = 1)
    private Integer id;

    @Column(name = "descricao_imovel")
    private String descricaoImovel;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "data_venda")
    private LocalDateTime dataVenda;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Column(name = "qtd_parcelas")
    private Integer qtdParcelas;

    @Column(name = "comprador_nome")
    private String compradorNome;

    @Column(name = "comprador_contato")
    private String compradorContato;

    @OneToMany(mappedBy = "venda")
    private List<ParcelaEntity> parcelas;

    @OneToMany(mappedBy = "venda")
    @JsonIgnore
    private List<ComissaoEntity> comissoes;



}
