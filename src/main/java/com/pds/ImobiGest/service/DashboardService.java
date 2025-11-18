package com.pds.ImobiGest.service;

import com.pds.ImobiGest.dto.dashboard.DashboardDTO;
import com.pds.ImobiGest.repository.ComissaoRepository;
import com.pds.ImobiGest.repository.VendaRepository;
import com.pds.ImobiGest.entity.ImobiliariaEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.math.RoundingMode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ImobiliariaService imobiliariaService;
    private final ComissaoService comissaoService;
    private final VendaService vendaService;
    private final VendaRepository vendaRepository;
    private final ComissaoRepository comissaoRepository;

    public DashboardDTO getDashboardData(Integer idImobiliaria) throws RegraDeNegocioException {
        ImobiliariaEntity imobiliaria = imobiliariaService.getById(idImobiliaria);

        DashboardDTO dashboard = new DashboardDTO();

        // Meta da imobiliária
        dashboard.setMetaImobiliaria(imobiliaria.getMeta());

        // Calcular vendas totais do ano atual
        // BigDecimal vendasTotaisAno = calcularVendasTotaisAno(idImobiliaria);

        BigDecimal comissaoTotal = comissaoRepository.somarComissoesPorImobiliariaEAno(idImobiliaria, LocalDate.now().getYear());

        // Quanto falta para bater a meta
        BigDecimal valorParaMeta = imobiliaria.getMeta().subtract(comissaoTotal);
        dashboard.setValorParaMeta(valorParaMeta.compareTo(BigDecimal.ZERO) > 0 ? valorParaMeta : BigDecimal.ZERO);

        // Comissões
        dashboard.setComissaoGeralTotal(calcularComissaoGeralTotal(idImobiliaria));
        //dashboard.setComissaoGeralEquipe(calcularComissaoGeralEquipe(idImobiliaria));
        dashboard.setComissaoSocios(calcularComissaoSocios(idImobiliaria));
        dashboard.setComissaoCorretores(calcularComissaoCorretores(idImobiliaria));

        // Médias
        //dashboard.setMediasMensalAnoEquipe(calcularMediaMensalAnoEquipe(idImobiliaria));
        //dashboard.setMediaPeriodoEquipe(calcularMediaPeriodoEquipe(idImobiliaria));
        dashboard.setMediaMensalAnoComissao(calcularMediaMensalAnoComissao(idImobiliaria));
        dashboard.setMediaPeriodoComissao(calcularMediaPeriodoComissao(idImobiliaria));

        return dashboard;
    }


    private BigDecimal calcularComissaoGeralTotal(Integer idImobiliaria) {
        int anoAtual = Year.now().getValue();
        return comissaoRepository.somarComissoesPorImobiliariaEAno(idImobiliaria, anoAtual);
    }

   // private BigDecimal calcularComissaoGeralTotalPeriodo(Integer idImobiliaria) {
        // Implementar lógica para calcular comissão geral da equipe
//        return BigDecimal.ZERO;
  //  }

//    private BigDecimal calcularComissaoGeralEquipe(Integer idImobiliaria) {
//        // Implementar lógica para calcular comissão geral da equipe
//        return BigDecimal.ZERO;
//    }

    private BigDecimal calcularComissaoSocios(Integer idImobiliaria) {
        return comissaoRepository.somarComissoesSocios(idImobiliaria);
    }

    private BigDecimal calcularComissaoCorretores(Integer idImobiliaria) {
        return comissaoRepository.somarComissoesCorretores(idImobiliaria);
    }

//    private BigDecimal calcularMediaMensalAnoEquipe(Integer idImobiliaria) {
//        // Implementar lógica para calcular média mensal do ano da equipe
//        return BigDecimal.ZERO;
//    }
//
//    private BigDecimal calcularMediaPeriodoEquipe(Integer idImobiliaria) {
//        // Implementar lógica para calcular média do período da equipe
//        return BigDecimal.ZERO;
//    }

    private BigDecimal calcularMediaMensalAnoComissao(Integer idImobiliaria) {
        int anoAtual = Year.now().getValue();
        BigDecimal totalComissoes = comissaoRepository.somarComissoesPorImobiliariaEAno(idImobiliaria, anoAtual);

        // Calcular quantos meses já passaram no ano atual (incluindo o mês atual)
        int mesAtual = LocalDate.now().getMonthValue();

        // Evitar divisão por zero
        if (mesAtual == 0) {
            return BigDecimal.ZERO;
        }

        return totalComissoes.divide(BigDecimal.valueOf(mesAtual), 2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal calcularMediaPeriodoComissao(Integer idImobiliaria) {
        // Buscar o total de comissões (sem filtro de ano)
        BigDecimal totalComissoes = comissaoRepository.somarTodasComissoesPorImobiliaria(idImobiliaria);

        // Buscar a quantidade de meses desde a primeira comissão até agora
        Integer mesesOperacao = comissaoRepository.contarMesesOperacao(idImobiliaria);

        // Se não há meses de operação ou comissões, retorna zero
        if (mesesOperacao == null || mesesOperacao == 0) {
            return BigDecimal.ZERO;
        }

        return totalComissoes.divide(BigDecimal.valueOf(mesesOperacao), 2, BigDecimal.ROUND_HALF_UP);
    }

    public DashboardDTO getDashboardDataPorPeriodo(Integer idImobiliaria, LocalDate dataInicio, LocalDate dataFim) throws RegraDeNegocioException {
        ImobiliariaEntity imobiliaria = imobiliariaService.getById(idImobiliaria);

        DashboardDTO dashboard = new DashboardDTO();

        // Meta da imobiliária
        dashboard.setMetaImobiliaria(imobiliaria.getMeta());

        BigDecimal comissaoTotalPeriodo = comissaoRepository.somarComissoesPorImobiliariaPeriodo(idImobiliaria, dataInicio, dataFim);

        // Quanto falta para bater a meta
        BigDecimal valorParaMeta = imobiliaria.getMeta().subtract(comissaoTotalPeriodo);
        dashboard.setValorParaMeta(valorParaMeta.compareTo(BigDecimal.ZERO) > 0 ? valorParaMeta : BigDecimal.ZERO);

        // Comissões do período
        dashboard.setComissaoGeralTotal(comissaoTotalPeriodo);
        dashboard.setComissaoSocios(calcularComissaoSociosPeriodo(idImobiliaria, dataInicio, dataFim));
        dashboard.setComissaoCorretores(calcularComissaoCorretoresPeriodo(idImobiliaria, dataInicio, dataFim));

        // Média mensal do período
        dashboard.setMediaMensalAnoComissao(calcularMediaMensalPeriodoComissao(idImobiliaria, dataInicio, dataFim));

        return dashboard;
    }

    private BigDecimal calcularComissaoSociosPeriodo(Integer idImobiliaria, LocalDate dataInicio, LocalDate dataFim) {
        return comissaoRepository.somarComissoesSociosPeriodo(idImobiliaria, dataInicio, dataFim);
    }

    private BigDecimal calcularComissaoCorretoresPeriodo(Integer idImobiliaria, LocalDate dataInicio, LocalDate dataFim) {
        return comissaoRepository.somarComissoesCorretoresPeriodo(idImobiliaria, dataInicio, dataFim);
    }

    private BigDecimal calcularMediaMensalPeriodoComissao(Integer idImobiliaria, LocalDate dataInicio, LocalDate dataFim) {
        BigDecimal totalComissoes = comissaoRepository.somarComissoesPorImobiliariaPeriodo(idImobiliaria, dataInicio, dataFim);

        // Calcular número de meses no período
        long mesesNoPeriodo = ChronoUnit.MONTHS.between(dataInicio.withDayOfMonth(1), dataFim.withDayOfMonth(1)) + 1;

        if (mesesNoPeriodo == 0) {
            return BigDecimal.ZERO;
        }

        return totalComissoes.divide(BigDecimal.valueOf(mesesNoPeriodo), 2, RoundingMode.HALF_UP);
    }
}