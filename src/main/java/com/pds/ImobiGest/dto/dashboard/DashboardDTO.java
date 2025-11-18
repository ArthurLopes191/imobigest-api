package com.pds.ImobiGest.dto.dashboard;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DashboardDTO {
    private BigDecimal metaImobiliaria;
    private BigDecimal valorParaMeta;
    private BigDecimal comissaoGeralTotal;
    private BigDecimal comissaoGeralEquipe;
    private BigDecimal comissaoSocios;
    private BigDecimal comissaoCorretores;
    private BigDecimal mediasMensalAnoEquipe;
    private BigDecimal mediaPeriodoEquipe;
    private BigDecimal mediaMensalAnoComissao;
    private BigDecimal mediaPeriodoComissao;
}