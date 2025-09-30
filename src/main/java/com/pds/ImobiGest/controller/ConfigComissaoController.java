package com.pds.ImobiGest.controller;

import com.pds.ImobiGest.controller.documentation.ConfigComissaoControllerDoc;
import com.pds.ImobiGest.dto.configComissao.ConfigComissaoCreateDTO;
import com.pds.ImobiGest.dto.configComissao.ConfigComissaoDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import com.pds.ImobiGest.service.ConfigComissaoService;
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
@RequestMapping("/config-comissao")
public class ConfigComissaoController implements ConfigComissaoControllerDoc {

    private final ConfigComissaoService configComissaoService;

    @PostMapping
    public ResponseEntity<ConfigComissaoDTO> create(@Valid @RequestBody ConfigComissaoCreateDTO configComissaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(configComissaoService.create(configComissaoCreateDTO),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigComissaoDTO> update(@PathVariable("id") Integer id, @RequestBody @Valid ConfigComissaoCreateDTO configComissaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(configComissaoService.update(id, configComissaoCreateDTO), HttpStatus.OK);
    }

    @GetMapping
    public List<ConfigComissaoDTO> list(){
        return configComissaoService.list();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        configComissaoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigComissaoDTO> listById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(configComissaoService.listById(id), HttpStatus.OK);
    }
}
