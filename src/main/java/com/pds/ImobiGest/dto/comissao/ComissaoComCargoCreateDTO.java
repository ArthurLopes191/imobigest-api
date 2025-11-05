package com.pds.ImobiGest.dto.comissao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ComissaoComCargoCreateDTO {
    @NotNull(message = "ID da venda é obrigatório")
    private Integer idVenda;

    @NotNull(message = "ID do profissional é obrigatório")
    private Integer idProfissional;

    @NotNull(message = "Lista de cargos é obrigatória")
    private List<Integer> idsCargos;
}