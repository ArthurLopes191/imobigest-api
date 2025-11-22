package com.pds.ImobiGest.service;

import com.pds.ImobiGest.dto.comissao.ComissaoComCargoCreateDTO;
import com.pds.ImobiGest.dto.comissao.ComissaoCreateDTO;
import com.pds.ImobiGest.dto.comissao.ComissaoDTO;
import com.pds.ImobiGest.dto.configComissao.ConfigComissaoDTO;
import com.pds.ImobiGest.dto.profissionalCargo.ProfissionalCargoDTO;
import com.pds.ImobiGest.entity.ComissaoEntity;
import com.pds.ImobiGest.entity.ProfissionalEntity;
import com.pds.ImobiGest.entity.VendaEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.ComissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComissaoService {

    private final VendaService vendaService;
    private final ProfissionalService profissionalService;
    private final ComissaoRepository comissaoRepository;
    private final ProfissionalCargoService profissionalCargoService;
    private final ConfigComissaoService configComissaoService;

    public ComissaoDTO create(ComissaoCreateDTO comissaoCreateDTO) throws RegraDeNegocioException{
        ComissaoEntity comissao = new ComissaoEntity();
        comissao.setValorComissao(comissaoCreateDTO.getValorComissao());
        comissao.setPercentual(comissaoCreateDTO.getPercentual());
        comissao.setTipoComissao("MANUAL");

        VendaEntity venda = vendaService.getById(comissaoCreateDTO.getIdVenda());
        comissao.setVenda(venda);

        ProfissionalEntity profissional = profissionalService.getById(comissaoCreateDTO.getIdProfissional());

        if (!profissional.getImobiliaria().getId().equals(venda.getImobiliaria().getId())) {
            throw new RegraDeNegocioException("O profissional deve pertencer à mesma imobiliária da venda");
        }

        comissao.setProfissional(profissional);

        ComissaoEntity saved = comissaoRepository.save(comissao);
        return convertToDTO(saved);
    }

    public ComissaoDTO update(Integer id, ComissaoCreateDTO comissaoCreateDTO) throws RegraDeNegocioException {
        ComissaoEntity comissao = getById(id);
        comissao.setPercentual(comissaoCreateDTO.getPercentual());
        comissao.setValorComissao(comissaoCreateDTO.getValorComissao());

        VendaEntity venda = vendaService.getById(comissaoCreateDTO.getIdVenda());
        comissao.setVenda(venda);

        ProfissionalEntity profissional = profissionalService.getById(comissaoCreateDTO.getIdProfissional());

        if (!profissional.getImobiliaria().getId().equals(venda.getImobiliaria().getId())) {
            throw new RegraDeNegocioException("O profissional deve pertencer à mesma imobiliária da venda");
        }

        comissao.setProfissional(profissional);

        ComissaoEntity updated = comissaoRepository.save(comissao);
        return convertToDTO(updated);
    }

    public List<ComissaoDTO> list() {
        return comissaoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        ComissaoEntity comissao = getById(id);
        comissaoRepository.delete(comissao);
    }

    public ComissaoDTO listById(Integer id) throws RegraDeNegocioException {
        ComissaoEntity comissao = getById(id);
        return convertToDTO(comissao);
    }

    public ComissaoDTO createComCargoCalculado(ComissaoComCargoCreateDTO dto) throws RegraDeNegocioException {
        // Validar venda e profissional
        VendaEntity venda = vendaService.getById(dto.getIdVenda());
        ProfissionalEntity profissional = profissionalService.getById(dto.getIdProfissional());

        if (!profissional.getImobiliaria().getId().equals(venda.getImobiliaria().getId())) {
            throw new RegraDeNegocioException("O profissional deve pertencer à mesma imobiliária da venda");
        }

        // Validar se profissional possui todos os cargos informados
        List<ProfissionalCargoDTO> cargosProfissional = profissionalCargoService.listByIdProfissional(dto.getIdProfissional());
        List<Integer> cargosValidos = cargosProfissional.stream()
                .map(ProfissionalCargoDTO::getIdCargo)
                .collect(Collectors.toList());

        boolean todosCargosSaoValidos = cargosValidos.containsAll(dto.getIdsCargos());
        if (!todosCargosSaoValidos) {
            throw new RegraDeNegocioException("Profissional não possui todos os cargos informados");
        }

        // Buscar configurações de comissão para cada cargo
        List<ConfigComissaoDTO> configsComissao = dto.getIdsCargos().stream()
                .map(idCargo -> {
                    try {
                        return configComissaoService.findByImobiliariaIdAndCargoId(
                                profissional.getImobiliaria().getId(), idCargo);
                    } catch (RegraDeNegocioException e) {
                        throw new RuntimeException("Configuração de comissão não encontrada para cargo: " + idCargo);
                    }
                })
                .collect(Collectors.toList());

        // Somar percentuais
        BigDecimal percentualTotal = configsComissao.stream()
                .map(ConfigComissaoDTO::getPercentual)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcular valor da comissão usando o valor da venda
        BigDecimal valorComissao = venda.getValorTotal()
                .multiply(percentualTotal)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        // Criar comissão
        ComissaoEntity comissao = new ComissaoEntity();
        comissao.setValorComissao(valorComissao);
        comissao.setPercentual(percentualTotal);
        comissao.setVenda(venda);
        comissao.setProfissional(profissional);
        comissao.setTipoComissao("MANUAL");

        ComissaoEntity saved = comissaoRepository.save(comissao);
        return convertToDTO(saved);
    }

    public ComissaoEntity getById(Integer id) throws RegraDeNegocioException {
        return comissaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Comissão não encontrada"));
    }

    private ComissaoDTO convertToDTO(ComissaoEntity comissao){
        ComissaoDTO comissaoDTO = new ComissaoDTO();
        comissaoDTO.setId(comissao.getId());
        comissaoDTO.setPercentual(comissao.getPercentual());
        comissaoDTO.setValorComissao(comissao.getValorComissao());
        comissaoDTO.setIdVenda(comissao.getVenda().getId());
        comissaoDTO.setIdProfissional(comissao.getProfissional().getId());
        comissaoDTO.setTipoComissao(comissao.getTipoComissao());
        return comissaoDTO;
    }
}
