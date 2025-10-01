package com.pds.ImobiGest.controller.documentation;

import com.pds.ImobiGest.dto.profissionalCargo.ProfissionalCargoCreateDTO;
import com.pds.ImobiGest.dto.profissionalCargo.ProfissionalCargoDTO;
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

@Tag(name = "Profissional Cargo", description = "Operações relacionadas à associação entre profissionais e cargos")
public interface ProfissionalCargoDoc {

    @Operation(summary = "Associar profissional a cargo", description = "Cria uma nova associação entre um profissional e um cargo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou profissional já possui este cargo"),
            @ApiResponse(responseCode = "404", description = "Profissional ou cargo não encontrado")
    })
    ResponseEntity<ProfissionalCargoDTO> create(@Valid @RequestBody ProfissionalCargoCreateDTO profissionalCargoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar associação profissional-cargo", description = "Atualiza uma associação existente entre profissional e cargo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou profissional já possui este cargo"),
            @ApiResponse(responseCode = "404", description = "Associação, profissional ou cargo não encontrado")
    })
    ResponseEntity<ProfissionalCargoDTO> update(@PathVariable("id") Integer id, @RequestBody @Valid ProfissionalCargoCreateDTO profissionalCargoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Listar todas as associações", description = "Retorna todas as associações entre profissionais e cargos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de associações retornada com sucesso")
    })
    List<ProfissionalCargoDTO> list();

    @Operation(summary = "Excluir associação", description = "Remove uma associação entre profissional e cargo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Buscar associação por ID", description = "Retorna uma associação específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação encontrada"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    ResponseEntity<ProfissionalCargoDTO> listById(@PathVariable("id") Integer id) throws RegraDeNegocioException;
}