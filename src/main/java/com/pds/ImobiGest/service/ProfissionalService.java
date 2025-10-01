package com.pds.ImobiGest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.ImobiGest.dto.profissional.ProfissionalCreateDTO;
import com.pds.ImobiGest.dto.profissional.ProfissionalDTO;
import com.pds.ImobiGest.entity.ConfigComissaoEntity;
import com.pds.ImobiGest.entity.ImobiliariaEntity;
import com.pds.ImobiGest.entity.ProfissionalEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final ImobiliariaService imobiliariaService;

    public ProfissionalDTO create(ProfissionalCreateDTO profissionalCreateDTO) throws RegraDeNegocioException {
        ProfissionalEntity profissional = new ProfissionalEntity();
        profissional.setNome(profissionalCreateDTO.getNome());

        ImobiliariaEntity imobiliaria = imobiliariaService.getById(profissionalCreateDTO.getIdImobiliaria());

        profissional.setImobiliaria(imobiliaria);

        ProfissionalEntity saved = profissionalRepository.save(profissional);

        return convertToDTO(saved);
    }

    public ProfissionalEntity getById(Integer id) throws RegraDeNegocioException {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));
    }

    private ProfissionalDTO convertToDTO(ProfissionalEntity entity){
        ProfissionalDTO dto = new ProfissionalDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setIdImobiliaria(entity.getImobiliaria().getId());

        return dto;
    }
}
