package com.pds.ImobiGest.service;

import com.pds.ImobiGest.dto.comissao.ComissaoCargosDTO;
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
import java.util.List;
import java.util.stream.Collectors;

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

        BigDecimal comissaoTotal = comissaoRepository.somarComissoesPorImobiliariaEAno(idImobiliaria, LocalDate.now().getYear());

        // Quanto falta para bater a meta
        BigDecimal valorParaMeta = imobiliaria.getMeta().subtract(comissaoTotal);
        dashboard.setValorParaMeta(valorParaMeta.compareTo(BigDecimal.ZERO) > 0 ? valorParaMeta : BigDecimal.ZERO);

        // Comissões
        dashboard.setComissaoGeralTotal(calcularComissaoGeralTotal(idImobiliaria));
        dashboard.setComissoesPorCargo(calcularComissoesPorCargo(idImobiliaria));

        // Adicionar comissões separadas por tipo
        dashboard.setComissoesAutomaticasPorCargo(calcularComissoesAutomaticasPorCargo(idImobiliaria));
        dashboard.setComissoesManuaisPorCargo(calcularComissoesManuaisPorCargo(idImobiliaria));

        // Médias
        dashboard.setMediaMensalAnoComissao(calcularMediaMensalAnoComissao(idImobiliaria));
        dashboard.setMediaPeriodoComissao(calcularMediaPeriodoComissao(idImobiliaria));

        return dashboard;
    }


    private BigDecimal calcularComissaoGeralTotal(Integer idImobiliaria) {
        int anoAtual = Year.now().getValue();
        return comissaoRepository.somarComissoesPorImobiliariaEAno(idImobiliaria, anoAtual);
    }

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
        dashboard.setComissoesPorCargo(calcularComissoesPorCargoPeriodo(idImobiliaria, dataInicio, dataFim));

        // Adicionar comissões separadas por tipo para período
        dashboard.setComissoesAutomaticasPorCargo(calcularComissoesAutomaticasPorCargoPeriodo(idImobiliaria, dataInicio, dataFim));
        dashboard.setComissoesManuaisPorCargo(calcularComissoesManuaisPorCargoPeriodo(idImobiliaria, dataInicio, dataFim));

        // Média mensal do período
        dashboard.setMediaMensalAnoComissao(calcularMediaMensalPeriodoComissao(idImobiliaria, dataInicio, dataFim));

        return dashboard;
    }

    private List<ComissaoCargosDTO> calcularComissoesPorCargo(Integer idImobiliaria) {
        int anoAtual = Year.now().getValue();
        List<Object[]> resultados = comissaoRepository.somarComissoesPorCargoEAno(idImobiliaria, anoAtual);

        return resultados.stream()
                .map(resultado -> new ComissaoCargosDTO(
                        (String) resultado[0],
                        (BigDecimal) resultado[1]
                ))
                .collect(Collectors.toList());
    }

    private List<ComissaoCargosDTO> calcularComissoesPorCargoPeriodo(Integer idImobiliaria, LocalDate dataInicio, LocalDate dataFim) {
        List<Object[]> resultados = comissaoRepository.somarComissoesPorCargoPeriodo(idImobiliaria, dataInicio, dataFim);

        return resultados.stream()
                .map(resultado -> new ComissaoCargosDTO(
                        (String) resultado[0],
                        (BigDecimal) resultado[1]
                ))
                .collect(Collectors.toList());
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

    // Adicione estes métodos ao DashboardService

    private List<ComissaoCargosDTO> calcularComissoesAutomaticasPorCargo(Integer idImobiliaria) {
        int anoAtual = Year.now().getValue();
        List<Object[]> resultados = comissaoRepository.somarComissoesAutomaticasPorCargoEAno(idImobiliaria, anoAtual);

        return resultados.stream()
                .map(resultado -> new ComissaoCargosDTO(
                        (String) resultado[0],
                        (BigDecimal) resultado[1]
                ))
                .collect(Collectors.toList());
    }

    private List<ComissaoCargosDTO> calcularComissoesManuaisPorCargo(Integer idImobiliaria) {
        int anoAtual = Year.now().getValue();
        List<Object[]> resultados = comissaoRepository.somarComissoesManuaisPorCargoEAno(idImobiliaria, anoAtual);

        return resultados.stream()
                .map(resultado -> new ComissaoCargosDTO(
                        (String) resultado[0],
                        (BigDecimal) resultado[1]
                ))
                .collect(Collectors.toList());
    }

    private List<ComissaoCargosDTO> calcularComissoesAutomaticasPorCargoPeriodo(Integer idImobiliaria, LocalDate dataInicio, LocalDate dataFim) {
        List<Object[]> resultados = comissaoRepository.somarComissoesPorCargoTipoEPeriodo(idImobiliaria, dataInicio, dataFim, "AUTOMATICA");

        return resultados.stream()
                .map(resultado -> new ComissaoCargosDTO(
                        (String) resultado[0],
                        (BigDecimal) resultado[1]
                ))
                .collect(Collectors.toList());
    }

    private List<ComissaoCargosDTO> calcularComissoesManuaisPorCargoPeriodo(Integer idImobiliaria, LocalDate dataInicio, LocalDate dataFim) {
        List<Object[]> resultados = comissaoRepository.somarComissoesPorCargoTipoEPeriodo(idImobiliaria, dataInicio, dataFim, "MANUAL");

        return resultados.stream()
                .map(resultado -> new ComissaoCargosDTO(
                        (String) resultado[0],
                        (BigDecimal) resultado[1]
                ))
                .collect(Collectors.toList());
    }
}