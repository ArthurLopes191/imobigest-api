package com.pds.ImobiGest.controller.documentation;

import com.pds.ImobiGest.dto.cargo.CargoCreateDTO;
import com.pds.ImobiGest.dto.cargo.CargoDTO;
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

@Tag(name = "Cargo", description = "Operações relacionadas ao gerenciamento de cargos")
public interface CargoControllerDoc {

    @Operation(summary = "Criar novo cargo", description = "Cria um novo cargo no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cargo criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CargoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
                    content = @Content)
    })
    ResponseEntity<CargoDTO> create(@Valid @RequestBody CargoCreateDTO cargoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar cargo", description = "Atualiza os dados de um cargo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargo atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CargoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
                    content = @Content)
    })
    ResponseEntity<CargoDTO> update(
            @Parameter(description = "ID do cargo a ser atualizado", required = true)
            @PathVariable("id") Integer id,
            @Valid @RequestBody CargoCreateDTO cargoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Listar todos os cargos", description = "Retorna uma lista com todos os cargos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cargos retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CargoDTO.class)))
    })
    List<CargoDTO> list();

    @Operation(summary = "Excluir cargo", description = "Remove um cargo do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Erro de regra de negócio",
                    content = @Content)
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID do cargo a ser excluído", required = true)
            @PathVariable("id") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Buscar cargo por ID", description = "Retorna os dados de um cargo específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargo encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CargoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado",
                    content = @Content)
    })
    ResponseEntity<CargoDTO> listById(
            @Parameter(description = "ID do cargo a ser buscado", required = true)
            @PathVariable("id") Integer id) throws RegraDeNegocioException;
}