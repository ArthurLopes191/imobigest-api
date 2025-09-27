package com.pds.ImobiGest.dto.profissional;


import com.pds.ImobiGest.entity.ComissaoEntity;
import com.pds.ImobiGest.entity.ProfissionalCargoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfissionalDTO {
    private String nome;
    private List<ComissaoEntity> comissoes;
    private List<ProfissionalCargoEntity> cargos;
}
