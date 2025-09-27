package com.pds.ImobiGest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.ImobiGest.dto.profissional.ProfissionalCreateDTO;
import com.pds.ImobiGest.dto.profissional.ProfissionalDTO;
import com.pds.ImobiGest.entity.ProfissionalEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final ObjectMapper objectMapper;

    public ProfissionalDTO create(ProfissionalCreateDTO profissionalCreateDTO) throws RegraDeNegocioException {
        ProfissionalEntity profissional = new ProfissionalEntity();
        profissional.setNome(profissionalCreateDTO.getNome());

        //TODO fazer esquema de cargos
        //profissional.setCargos(profissionalCreateDTO.getCargos());

        ProfissionalEntity saved = profissionalRepository.save(profissional);

        return convertToDTO(saved);
    }



    private ProfissionalDTO convertToDTO(ProfissionalEntity entity){
        return objectMapper.convertValue(entity, ProfissionalDTO.class);
    }
}
