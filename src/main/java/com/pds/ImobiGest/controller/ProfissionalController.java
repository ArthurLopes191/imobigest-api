package com.pds.ImobiGest.controller;

import com.pds.ImobiGest.controller.documentation.ProfissionalControllerDoc;
import com.pds.ImobiGest.dto.profissional.ProfissionalCompletoDTO;
import com.pds.ImobiGest.dto.profissional.ProfissionalCreateDTO;
import com.pds.ImobiGest.dto.profissional.ProfissionalDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.service.ProfissionalService;
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
@RequestMapping("/profissional")
public class ProfissionalController implements ProfissionalControllerDoc {

    private final ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<ProfissionalDTO> create(@Valid @RequestBody ProfissionalCreateDTO profissionalCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(profissionalService.create(profissionalCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> update(@PathVariable("id") Integer id, @RequestBody @Valid ProfissionalCreateDTO profissionalCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(profissionalService.update(id, profissionalCreateDTO), HttpStatus.OK);
    }

    @GetMapping
    public List<ProfissionalDTO> list(){
        return profissionalService.list();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        profissionalService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> listById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(profissionalService.listById(id), HttpStatus.OK);
    }

    @GetMapping("/completo")
    public List<ProfissionalCompletoDTO> listCompleto() {
        return profissionalService.listCompleto();
    }

    @GetMapping("/completo/{id}")
    public ResponseEntity<ProfissionalCompletoDTO> listCompletoById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(profissionalService.listCompletoById(id), HttpStatus.OK);
    }
}
