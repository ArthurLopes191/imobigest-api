package com.pds.ImobiGest.dto.dashboard;

import com.pds.ImobiGest.dto.comissao.ComissaoCargosDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardDTO {
    private BigDecimal metaImobiliaria;
    private BigDecimal valorParaMeta;
    private BigDecimal comissaoGeralTotal;
    //private BigDecimal comissaoGeralEquipe;
    //private BigDecimal comissaoSocios;
    //private BigDecimal comissaoCorretores;
    //private BigDecimal mediasMensalAnoEquipe;
    //private BigDecimal mediaPeriodoEquipe;
    private BigDecimal mediaMensalAnoComissao;
    private BigDecimal mediaPeriodoComissao;

    private List<ComissaoCargosDTO> comissoesPorCargo;
}