package com.pds.ImobiGest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.ImobiGest.dto.configComissao.ConfigComissaoCreateDTO;
import com.pds.ImobiGest.dto.configComissao.ConfigComissaoDTO;
import com.pds.ImobiGest.entity.CargoEntity;
import com.pds.ImobiGest.entity.ConfigComissaoEntity;
import com.pds.ImobiGest.entity.ImobiliariaEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.ConfigComissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfigComissaoService {

    private final ObjectMapper objectMapper;
    private final ConfigComissaoRepository configComissaoRepository;
    private final ImobiliariaService imobiliariaService;
    private final CargoService cargoService;

    public ConfigComissaoDTO create(ConfigComissaoCreateDTO configComissaoCreateDTO) throws RegraDeNegocioException {

        Optional<ConfigComissaoEntity> existente = configComissaoRepository
                .findByImobiliariaIdAndCargoId(configComissaoCreateDTO.getIdImobiliaria(), configComissaoCreateDTO.getIdCargo());

        if (existente.isPresent()) {
            throw new RegraDeNegocioException("Já existe configuração de comissão para este cargo nesta imobiliária");
        }

        ConfigComissaoEntity configComissaoEntity = new ConfigComissaoEntity();

        ImobiliariaEntity imobiliaria = imobiliariaService.getById(configComissaoCreateDTO.getIdImobiliaria());
        CargoEntity cargo = cargoService.getById(configComissaoCreateDTO.getIdCargo());

        configComissaoEntity.setImobiliaria(imobiliaria);
        configComissaoEntity.setCargo(cargo);
        configComissaoEntity.setPercentual(configComissaoCreateDTO.getPercentual());

        ConfigComissaoEntity saved = configComissaoRepository.save(configComissaoEntity);
        return convertToDTO(saved);
    }

    public ConfigComissaoDTO update(Integer id, ConfigComissaoCreateDTO configComissaoCreateDTO) throws RegraDeNegocioException {
        Optional<ConfigComissaoEntity> existente = configComissaoRepository
                .findByImobiliariaIdAndCargoIdAndIdNot(configComissaoCreateDTO.getIdImobiliaria(), configComissaoCreateDTO.getIdCargo(), id);

        if (existente.isPresent()) {
            throw new RegraDeNegocioException("Já existe configuração de comissão para este cargo nesta imobiliária");
        }

        ConfigComissaoEntity configComissaoEntity = getById(id);

        ImobiliariaEntity imobiliaria = imobiliariaService.getById(configComissaoCreateDTO.getIdImobiliaria());
        CargoEntity cargo = cargoService.getById(configComissaoCreateDTO.getIdCargo());

        configComissaoEntity.setImobiliaria(imobiliaria);
        configComissaoEntity.setCargo(cargo);
        configComissaoEntity.setPercentual(configComissaoCreateDTO.getPercentual());

        configComissaoRepository.save(configComissaoEntity);
        return convertToDTO(configComissaoEntity);
    }

    public List<ConfigComissaoDTO> list(){
        return configComissaoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        ConfigComissaoEntity configComissaoEntity = getById(id);
        configComissaoRepository.delete(configComissaoEntity);
    }

    public ConfigComissaoDTO listById(Integer id) throws RegraDeNegocioException {
        ConfigComissaoEntity configComissaoEntity = getById(id);
        return convertToDTO(configComissaoEntity);
    }

    public ConfigComissaoEntity getById(Integer id) throws RegraDeNegocioException {
        return configComissaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Configuração de Comissão não encontrada"));
    }

    private ConfigComissaoDTO convertToDTO(ConfigComissaoEntity configComissaoEntity){
       ConfigComissaoDTO dto = new ConfigComissaoDTO();
       dto.setId(configComissaoEntity.getId());
       dto.setPercentual(configComissaoEntity.getPercentual());

       if(configComissaoEntity.getImobiliaria() != null){
              dto.setIdImobiliaria(configComissaoEntity.getImobiliaria().getId());
       }

       if(configComissaoEntity.getCargo() != null){
           dto.setIdCargo(configComissaoEntity.getCargo().getId());
       }
       return dto;
    }
}
