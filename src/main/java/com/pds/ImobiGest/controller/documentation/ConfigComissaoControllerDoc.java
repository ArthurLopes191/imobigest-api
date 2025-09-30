package com.pds.ImobiGest.controller.documentation;

import com.pds.ImobiGest.dto.configComissao.ConfigComissaoCreateDTO;
import com.pds.ImobiGest.dto.configComissao.ConfigComissaoDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Configuração de Comissão", description = "Operações relacionadas às configurações de comissão")
public interface ConfigComissaoControllerDoc {

    @Operation(summary = "Criar configuração de comissão", description = "Cria uma nova configuração de comissão para um cargo em uma imobiliária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configuração criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou configuração já existe"),
            @ApiResponse(responseCode = "404", description = "Imobiliária ou cargo não encontrado")
    })
    ResponseEntity<ConfigComissaoDTO> create(@Valid @RequestBody ConfigComissaoCreateDTO configComissaoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar configuração de comissão", description = "Atualiza uma configuração de comissão existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configuração atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Configuração, imobiliária ou cargo não encontrado")
    })
    ResponseEntity<ConfigComissaoDTO> update(@PathVariable("id") Integer id, @RequestBody @Valid ConfigComissaoCreateDTO configComissaoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Listar todas as configurações", description = "Retorna todas as configurações de comissão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de configurações retornada com sucesso")
    })
    List<ConfigComissaoDTO> list();

    @Operation(summary = "Excluir configuração", description = "Remove uma configuração de comissão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configuração excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Configuração não encontrada")
    })
    ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Buscar configuração por ID", description = "Retorna uma configuração específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configuração encontrada"),
            @ApiResponse(responseCode = "404", description = "Configuração não encontrada")
    })
    ResponseEntity<ConfigComissaoDTO> listById(@PathVariable("id") Integer id) throws RegraDeNegocioException;
}