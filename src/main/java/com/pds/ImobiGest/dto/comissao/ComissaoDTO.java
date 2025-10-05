package com.pds.ImobiGest.dto.comissao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComissaoDTO {

    private Integer id;
    private BigDecimal percentual;
    private BigDecimal valorComissao;
    private Integer idVenda;
    private Integer idProfissional;

}
