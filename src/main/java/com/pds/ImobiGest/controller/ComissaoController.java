package com.pds.ImobiGest.controller;

import com.pds.ImobiGest.controller.documentation.ComissaoControllerDoc;
import com.pds.ImobiGest.dto.comissao.ComissaoComCargoCreateDTO;
import com.pds.ImobiGest.dto.comissao.ComissaoCreateDTO;
import com.pds.ImobiGest.dto.comissao.ComissaoDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.service.ComissaoService;
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
@RequestMapping("/comissao")
public class ComissaoController implements ComissaoControllerDoc {

    private final ComissaoService comissaoService;

    @PostMapping
    public ResponseEntity<ComissaoDTO> create(@Valid @RequestBody ComissaoCreateDTO comissaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(comissaoService.create(comissaoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComissaoDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody ComissaoCreateDTO comissaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(comissaoService.update(id, comissaoCreateDTO), HttpStatus.OK);
    }

    @GetMapping
    public List<ComissaoDTO> list() {
        return comissaoService.list();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        comissaoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComissaoDTO> listById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(comissaoService.listById(id), HttpStatus.OK);
    }

    @PostMapping("/com-cargo")
    public ResponseEntity<ComissaoDTO> createComCargoCalculado(@Valid @RequestBody ComissaoComCargoCreateDTO dto) throws RegraDeNegocioException {
        return new ResponseEntity<>(comissaoService.createComCargoCalculado(dto), HttpStatus.CREATED);
    }
}
