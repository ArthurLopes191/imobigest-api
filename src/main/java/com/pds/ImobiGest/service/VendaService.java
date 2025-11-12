package com.pds.ImobiGest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.ImobiGest.dto.venda.VendaCreateDTO;
import com.pds.ImobiGest.dto.venda.VendaDTO;
import com.pds.ImobiGest.entity.ComissaoEntity;
import com.pds.ImobiGest.entity.ImobiliariaEntity;
import com.pds.ImobiGest.entity.VendaEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.ComissaoRepository;
import com.pds.ImobiGest.repository.VendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final ObjectMapper objectMapper;
    private final VendaRepository vendaRepository;
    private final ImobiliariaService imobiliariaService;
    private final ComissaoRepository comissaoRepository;

    public VendaDTO create(VendaCreateDTO vendaCreateDTO) throws RegraDeNegocioException {
        VendaEntity venda = new VendaEntity();
        venda.setDescricaoImovel(vendaCreateDTO.getDescricaoImovel());
        venda.setValorTotal(vendaCreateDTO.getValorTotal());
        venda.setDataVenda(vendaCreateDTO.getDataVenda());
        venda.setFormaPagamento(vendaCreateDTO.getFormaPagamento());
        venda.setQtdParcelas(vendaCreateDTO.getQtdParcelas());
        venda.setCompradorNome(vendaCreateDTO.getCompradorNome());
        venda.setCompradorContato(vendaCreateDTO.getCompradorContato());

        if (vendaCreateDTO.getIdImobiliaria() != null) {
            ImobiliariaEntity imobiliaria = imobiliariaService.getById(vendaCreateDTO.getIdImobiliaria());
            if (imobiliaria == null) {
                throw new RegraDeNegocioException("Imobiliária não encontrada");
            }
            venda.setImobiliaria(imobiliaria);
        } else {
            throw new RegraDeNegocioException("Id da Imobiliária é obrigatório");
        }

        VendaEntity saved = vendaRepository.save(venda);
        return convertToDTO(saved);
    }

    public VendaDTO update(Integer id, VendaCreateDTO vendaCreateDTO) throws RegraDeNegocioException {
        VendaEntity venda = getById(id);
        venda.setDescricaoImovel(vendaCreateDTO.getDescricaoImovel());
        venda.setValorTotal(vendaCreateDTO.getValorTotal());
        venda.setDataVenda(vendaCreateDTO.getDataVenda());
        venda.setFormaPagamento(vendaCreateDTO.getFormaPagamento());
        venda.setQtdParcelas(vendaCreateDTO.getQtdParcelas());
        venda.setCompradorNome(vendaCreateDTO.getCompradorNome());
        venda.setCompradorContato(vendaCreateDTO.getCompradorContato());

        if (vendaCreateDTO.getIdImobiliaria() != null) {
            ImobiliariaEntity imobiliaria = imobiliariaService.getById(vendaCreateDTO.getIdImobiliaria());
            if (imobiliaria == null) {
                throw new RegraDeNegocioException("Imobiliária não encontrada");
            }
            venda.setImobiliaria(imobiliaria);
        }
        VendaEntity updated = vendaRepository.save(venda);
        return convertToDTO(updated);

    }

    public List<VendaDTO> list() {
        return vendaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        VendaEntity venda = getById(id);

        List<ComissaoEntity> comissoes = comissaoRepository.findByVendaId(id);
        comissaoRepository.deleteAll(comissoes);

        vendaRepository.delete(venda);
    }

    public VendaDTO listById(Integer id) throws RegraDeNegocioException {
        VendaEntity venda = getById(id);
        return convertToDTO(venda);
    }

    public VendaEntity getById(Integer id) throws RegraDeNegocioException {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Venda não encontrada"));
    }

    private VendaDTO convertToDTO(VendaEntity vendaEntity) {
        VendaDTO vendaDTO = objectMapper.convertValue(vendaEntity, VendaDTO.class);

        if (vendaEntity.getImobiliaria() != null) {
            vendaDTO.setIdImobiliaria(vendaEntity.getImobiliaria().getId());
        }

        return vendaDTO;
    }
}
