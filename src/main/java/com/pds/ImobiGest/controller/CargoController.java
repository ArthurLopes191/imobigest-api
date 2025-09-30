package com.pds.ImobiGest.controller;

import com.pds.ImobiGest.controller.documentation.CargoControllerDoc;
import com.pds.ImobiGest.dto.cargo.CargoCreateDTO;
import com.pds.ImobiGest.dto.cargo.CargoDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/cargo")
public class CargoController implements CargoControllerDoc {

    private final CargoService cargoService;

    @PostMapping
    public ResponseEntity<CargoDTO> create(@Valid @RequestBody CargoCreateDTO cargoCreateDTO) throws RegraDeNegocioException{
        return new ResponseEntity<>(cargoService.create(cargoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDTO> update(@PathVariable("id") Integer id, @RequestBody @Valid CargoCreateDTO cargoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(cargoService.update(id, cargoCreateDTO), HttpStatus.OK);
    }

    @GetMapping
    public List<CargoDTO> list() {
        return cargoService.list();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        cargoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDTO> listById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(cargoService.listById(id), HttpStatus.OK);
    }
}
