package com.pds.ImobiGest.dto.profissional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfissionalDTO {

    private Integer id;
    private String nome;
    private Integer idImobiliaria;
}
