package com.pds.ImobiGest.controller.documentation;

import com.pds.ImobiGest.dto.Imobiliaria.ImobiliariaCreateDTO;
import com.pds.ImobiGest.dto.Imobiliaria.ImobiliariaDTO;
import com.pds.ImobiGest.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Imobiliária", description = "Operações relacionadas ao gerenciamento de imobiliárias")
public interface ImobiliariaControllerDoc {

    @Operation(summary = "Criar nova imobiliária", description = "Cria uma nova imobiliária no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imobiliária criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ImobiliariaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
                    content = @Content)
    })
    ResponseEntity<ImobiliariaDTO> create(@Valid @RequestBody ImobiliariaCreateDTO imobiliariaCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Listar todas as imobiliárias", description = "Retorna uma lista com todas as imobiliárias cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imobiliárias retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ImobiliariaDTO.class)))
    })
    List<ImobiliariaDTO> list();

    @Operation(summary = "Atualizar imobiliária", description = "Atualiza os dados de uma imobiliária existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imobiliária atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ImobiliariaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Imobiliária não encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
                    content = @Content)
    })
    ResponseEntity<ImobiliariaDTO> update(
            @Parameter(description = "ID da imobiliária a ser atualizada", required = true)
            @PathVariable("id") Integer id,
            @Valid @RequestBody ImobiliariaCreateDTO imobiliariaCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Excluir imobiliária", description = "Remove uma imobiliária do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imobiliária excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imobiliária não encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
                    content = @Content)
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID da imobiliária a ser excluída", required = true)
            @PathVariable("id") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Buscar imobiliária por ID", description = "Retorna os dados de uma imobiliária específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imobiliária encontrada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ImobiliariaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Imobiliária não encontrada",
                    content = @Content)
    })
    ResponseEntity<ImobiliariaDTO> listById(
            @Parameter(description = "ID da imobiliária a ser buscada", required = true)
            @PathVariable("id") Integer id) throws RegraDeNegocioException;
}