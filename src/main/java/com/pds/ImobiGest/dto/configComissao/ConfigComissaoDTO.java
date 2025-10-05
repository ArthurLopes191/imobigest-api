package com.pds.ImobiGest.dto.configComissao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigComissaoDTO {
    private Integer id;
    private Integer idImobiliaria;
    private Integer idCargo;
    private BigDecimal percentual;
}
