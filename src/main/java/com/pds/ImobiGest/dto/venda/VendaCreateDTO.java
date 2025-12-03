package com.pds.ImobiGest.dto.venda;

import com.pds.ImobiGest.enums.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendaCreateDTO {

    private String descricaoImovel;
    private BigDecimal valorTotal;
    private LocalDateTime dataVenda;
    private FormaPagamento formaPagamento;
    private Integer qtdParcelas;
    private String compradorNome;
    private String compradorContato;
    private String vendedorNome;
    private String vendedorContato;
    private BigDecimal comissaoComprador;
    private BigDecimal comissaoVendedor;
    private Integer idImobiliaria;
}
