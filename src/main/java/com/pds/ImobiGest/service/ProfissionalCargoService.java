package com.pds.ImobiGest.service;

import com.pds.ImobiGest.dto.profissionalCargo.ProfissionalCargoCreateDTO;
import com.pds.ImobiGest.dto.profissionalCargo.ProfissionalCargoDTO;
import com.pds.ImobiGest.entity.CargoEntity;
import com.pds.ImobiGest.entity.ProfissionalCargoEntity;
import com.pds.ImobiGest.entity.ProfissionalEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.ProfissionalCargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfissionalCargoService {

    private final ProfissionalCargoRepository profissionalCargoRepository;
    private final CargoService cargoService;
    private final ProfissionalService profissionalService;

    public ProfissionalCargoDTO create(ProfissionalCargoCreateDTO profissionalCargoCreateDTO) throws RegraDeNegocioException {

        Optional<ProfissionalCargoEntity> existente = profissionalCargoRepository
                .findByProfissionalIdAndCargoId(profissionalCargoCreateDTO.getIdProfissional(), profissionalCargoCreateDTO.getIdCargo());

        if (existente.isPresent()) {
            throw new RegraDeNegocioException("Profissional já possui este cargo");
        }

        ProfissionalCargoEntity profissionalCargoEntity = new ProfissionalCargoEntity();

        ProfissionalEntity profissionalEntity = profissionalService.getById(profissionalCargoCreateDTO.getIdProfissional());
        CargoEntity cargo = cargoService.getById(profissionalCargoCreateDTO.getIdCargo());

        profissionalCargoEntity.setProfissional(profissionalEntity);
        profissionalCargoEntity.setCargo(cargo);

        ProfissionalCargoEntity saved = profissionalCargoRepository.save(profissionalCargoEntity);
        return convertToDTO(saved);
    }

    public ProfissionalCargoDTO update(Integer id, ProfissionalCargoCreateDTO profissionalCargoCreateDTO) throws RegraDeNegocioException {
        Optional<ProfissionalCargoEntity> existente = profissionalCargoRepository
                .findByProfissionalIdAndCargoId(profissionalCargoCreateDTO.getIdProfissional(), profissionalCargoCreateDTO.getIdCargo());

        if (existente.isPresent() && !existente.get().getId().equals(id)) {
            throw new RegraDeNegocioException("Profissional já possui este cargo");
        }

        ProfissionalCargoEntity profissionalCargoEntity = getById(id);

        ProfissionalEntity profissionalEntity = profissionalService.getById(profissionalCargoCreateDTO.getIdProfissional());
        CargoEntity cargo = cargoService.getById(profissionalCargoCreateDTO.getIdCargo());

        profissionalCargoEntity.setProfissional(profissionalEntity);
        profissionalCargoEntity.setCargo(cargo);

        ProfissionalCargoEntity saved = profissionalCargoRepository.save(profissionalCargoEntity);
        return convertToDTO(saved);
    }

    public List<ProfissionalCargoDTO> list() {
        return profissionalCargoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        ProfissionalCargoEntity profissionalCargoEntity = getById(id);
        profissionalCargoRepository.delete(profissionalCargoEntity);
    }

    public ProfissionalCargoDTO listById(Integer id) throws RegraDeNegocioException {
        ProfissionalCargoEntity profissionalCargoEntity = getById(id);
        return convertToDTO(profissionalCargoEntity);
    }

    public ProfissionalCargoEntity getById(Integer id) throws RegraDeNegocioException {
        return profissionalCargoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional-Cargo não encontrado"));
    }

    private ProfissionalCargoDTO convertToDTO(ProfissionalCargoEntity profissionalCargoEntity){
        ProfissionalCargoDTO dto = new ProfissionalCargoDTO();
        dto.setId(profissionalCargoEntity.getId());
        dto.setIdProfissional(profissionalCargoEntity.getProfissional().getId());
        dto.setIdCargo(profissionalCargoEntity.getCargo().getId());

        return dto;
    }
}
