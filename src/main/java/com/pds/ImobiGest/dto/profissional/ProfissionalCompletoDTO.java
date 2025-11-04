package com.pds.ImobiGest.dto.profissional;

import com.pds.ImobiGest.dto.Imobiliaria.ImobiliariaResumoDTO;
import com.pds.ImobiGest.dto.cargo.CargoResumoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProfissionalCompletoDTO {
    private Integer id;
    private String nome;
    private Integer idImobiliaria;
    private ImobiliariaResumoDTO imobiliaria;
    private List<CargoResumoDTO> cargos;
}
