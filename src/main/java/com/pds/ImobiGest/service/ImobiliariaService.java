package com.pds.ImobiGest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.ImobiGest.dto.Imobiliaria.ImobiliariaCreateDTO;
import com.pds.ImobiGest.dto.Imobiliaria.ImobiliariaDTO;
import com.pds.ImobiGest.entity.ImobiliariaEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.ImobiliariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImobiliariaService {

    private final ObjectMapper objectMapper;
    private final ImobiliariaRepository imobiliariaRepository;

    public ImobiliariaDTO create(ImobiliariaCreateDTO imobiliariaCreateDTO) throws RegraDeNegocioException {
        ImobiliariaEntity imobiliariaEntity = new ImobiliariaEntity();
        imobiliariaEntity.setNome(imobiliariaCreateDTO.getNome());
        imobiliariaEntity.setMeta(imobiliariaCreateDTO.getMeta());

        ImobiliariaEntity saved = imobiliariaRepository.save(imobiliariaEntity);

        return convertToDTO(saved);
    }

    public List<ImobiliariaDTO> list(){
        return imobiliariaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ImobiliariaDTO update(Integer id, ImobiliariaCreateDTO imobiliariaCreateDTO) throws RegraDeNegocioException {
        ImobiliariaEntity imobiliariaEntity = getById(id);
        imobiliariaEntity.setNome(imobiliariaCreateDTO.getNome());
        imobiliariaEntity.setMeta(imobiliariaCreateDTO.getMeta());
        imobiliariaRepository.save(imobiliariaEntity);

        ImobiliariaDTO imobiliariaDTO = convertToDTO(imobiliariaEntity);

        return imobiliariaDTO;

    }

    public void delete(Integer id) throws RegraDeNegocioException {
        ImobiliariaEntity imobiliariaEntity = getById(id);
        imobiliariaRepository.delete(imobiliariaEntity);
    }

    public ImobiliariaDTO listById(Integer id) throws RegraDeNegocioException {
        ImobiliariaEntity imobiliariaEntity = getById(id);
        return convertToDTO(imobiliariaEntity);
    }

    private ImobiliariaDTO convertToDTO(ImobiliariaEntity entity){
        return objectMapper.convertValue(entity, ImobiliariaDTO.class);
    }

    public ImobiliariaEntity getById(Integer id) throws RegraDeNegocioException {
        return imobiliariaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Imobiliária não encontrada"));
    }
}
