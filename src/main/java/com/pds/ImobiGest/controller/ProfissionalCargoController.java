package com.pds.ImobiGest.controller;

import com.pds.ImobiGest.controller.documentation.ProfissionalCargoDoc;
import com.pds.ImobiGest.dto.profissionalCargo.ProfissionalCargoCreateDTO;
import com.pds.ImobiGest.dto.profissionalCargo.ProfissionalCargoDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.service.ProfissionalCargoService;
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
@RequestMapping("/profissional-cargo")
public class ProfissionalCargoController implements ProfissionalCargoDoc {

    private final ProfissionalCargoService profissionalCargoService;

    @PostMapping
    public ResponseEntity<ProfissionalCargoDTO> create(@Valid @RequestBody ProfissionalCargoCreateDTO profissionalCargoCreateDTO ) throws RegraDeNegocioException{
        return new ResponseEntity<>(profissionalCargoService.create(profissionalCargoCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalCargoDTO> update(@PathVariable("id") Integer id, @RequestBody @Valid ProfissionalCargoCreateDTO profissionalCargoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(profissionalCargoService.update(id, profissionalCargoCreateDTO), HttpStatus.OK);
    }

    @GetMapping
    public List<ProfissionalCargoDTO> list(){
        return profissionalCargoService.list();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        profissionalCargoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalCargoDTO> listById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(profissionalCargoService.listById(id), HttpStatus.OK);
    }
}
