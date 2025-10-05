package com.pds.ImobiGest.service;

import com.pds.ImobiGest.dto.comissao.ComissaoCreateDTO;
import com.pds.ImobiGest.dto.comissao.ComissaoDTO;
import com.pds.ImobiGest.entity.ComissaoEntity;
import com.pds.ImobiGest.entity.ProfissionalEntity;
import com.pds.ImobiGest.entity.VendaEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.ComissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComissaoService {

    private final VendaService vendaService;
    private final ProfissionalService profissionalService;
    private final ComissaoRepository comissaoRepository;

    public ComissaoDTO create(ComissaoCreateDTO comissaoCreateDTO) throws RegraDeNegocioException{
        ComissaoEntity comissao = new ComissaoEntity();
        comissao.setValorComissao(comissaoCreateDTO.getValorComissao());
        comissao.setPercentual(comissaoCreateDTO.getPercentual());

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
        return comissaoDTO;
    }
}
