package com.pds.ImobiGest.service;

import com.pds.ImobiGest.dto.parcela.ParcelaCreateDTO;
import com.pds.ImobiGest.dto.parcela.ParcelaDTO;
import com.pds.ImobiGest.entity.ParcelaEntity;
import com.pds.ImobiGest.entity.VendaEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.ParcelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParcelaService {

    private final ParcelaRepository parcelaRepository;
    private final VendaService vendaService;

    public ParcelaDTO create(ParcelaCreateDTO parcelaCreateDTO) throws RegraDeNegocioException {
        ParcelaEntity parcela = new ParcelaEntity();
        parcela.setNumeroParcela(parcelaCreateDTO.getNumeroParcela());
        parcela.setValorParcela(parcelaCreateDTO.getValorParcela());
        parcela.setDataVencimento(parcelaCreateDTO.getDataVencimento());
        parcela.setStatus(parcelaCreateDTO.getStatus());

        VendaEntity venda = vendaService.getById(parcelaCreateDTO.getIdVenda());

        parcela.setVenda(venda);

        ParcelaEntity saved = parcelaRepository.save(parcela);

        return convertToDTO(saved);
    }


    public ParcelaDTO update(Integer id, ParcelaCreateDTO parcelaCreateDTO) throws RegraDeNegocioException {
        ParcelaEntity parcela = getById(id);
        parcela.setNumeroParcela(parcelaCreateDTO.getNumeroParcela());
        parcela.setValorParcela(parcelaCreateDTO.getValorParcela());
        parcela.setDataVencimento(parcelaCreateDTO.getDataVencimento());
        parcela.setStatus(parcelaCreateDTO.getStatus());

        VendaEntity venda = vendaService.getById(parcelaCreateDTO.getIdVenda());

        parcela.setVenda(venda);

        ParcelaEntity updated = parcelaRepository.save(parcela);

        return convertToDTO(updated);
    }

    public List<ParcelaDTO> list() {
        return parcelaRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ParcelaDTO listById(Integer id) throws RegraDeNegocioException {
        ParcelaEntity parcela = getById(id);
        return convertToDTO(parcela);
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        ParcelaEntity parcela = getById(id);
        parcelaRepository.delete(parcela);
    }

    public ParcelaEntity getById(Integer id) throws RegraDeNegocioException {
        return parcelaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Parcela não encontrada"));
    }

    private ParcelaDTO convertToDTO(ParcelaEntity parcelaEntity) {
        ParcelaDTO parcelaDTO = new ParcelaDTO();
        parcelaDTO.setId(parcelaEntity.getId());
        parcelaDTO.setNumeroParcela(parcelaEntity.getNumeroParcela());
        parcelaDTO.setValorParcela(parcelaEntity.getValorParcela());
        parcelaDTO.setDataVencimento(parcelaEntity.getDataVencimento());
        parcelaDTO.setStatus(parcelaEntity.getStatus());
        if (parcelaEntity.getVenda() != null) {
            parcelaDTO.setIdVenda(parcelaEntity.getVenda().getId());
        }
        return parcelaDTO;
    }
}
