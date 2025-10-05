package com.pds.ImobiGest.dto.comissao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComissaoCreateDTO {

    private BigDecimal percentual;
    private BigDecimal valorComissao;
    private Integer idVenda;
    private Integer idProfissional;
}
