package com.pds.ImobiGest.dto.profissional;

import com.pds.ImobiGest.entity.ProfissionalCargoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfissionalCreateDTO {

    private String nome;
    private Integer idImobiliaria;
}
