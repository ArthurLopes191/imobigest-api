package com.pds.ImobiGest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.ImobiGest.dto.cargo.CargoCreateDTO;
import com.pds.ImobiGest.dto.cargo.CargoDTO;
import com.pds.ImobiGest.entity.CargoEntity;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final ObjectMapper objectMapper;
    private final CargoRepository cargoRepository;

    public CargoDTO create(CargoCreateDTO cargoCreateDTO) throws RegraDeNegocioException {
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setNome(cargoCreateDTO.getNome());
        CargoEntity saved = cargoRepository.save(cargoEntity);
        return convertToDTO(saved);
    }

    public CargoDTO update(Integer id, CargoCreateDTO cargoCreateDTO) throws RegraDeNegocioException {
        CargoEntity cargoEntity = getById(id);
        cargoEntity.setNome(cargoCreateDTO.getNome());
        cargoRepository.save(cargoEntity);
        return convertToDTO(cargoEntity);
    }

    public List<CargoDTO> list() {
        return cargoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        CargoEntity cargoEntity = getById(id);
        cargoRepository.delete(cargoEntity);
    }

    public CargoDTO listById(Integer id) throws RegraDeNegocioException {
        CargoEntity cargoEntity = getById(id);
        return convertToDTO(cargoEntity);
    }

    private CargoEntity getById(Integer id) throws RegraDeNegocioException {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado"));
    }

    private CargoDTO convertToDTO(CargoEntity cargoEntity){
        return objectMapper.convertValue(cargoEntity, CargoDTO.class);
    }
}
