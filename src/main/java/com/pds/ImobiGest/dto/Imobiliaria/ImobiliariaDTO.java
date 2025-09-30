package com.pds.ImobiGest.dto.Imobiliaria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImobiliariaDTO {
    private String nome;
    private BigDecimal meta;
}
