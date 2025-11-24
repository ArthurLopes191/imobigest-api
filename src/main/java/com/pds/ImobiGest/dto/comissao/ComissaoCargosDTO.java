package com.pds.ImobiGest.dto.comissao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComissaoCargosDTO {
    private String nomeCargo;
    private BigDecimal valorComissao;
}
