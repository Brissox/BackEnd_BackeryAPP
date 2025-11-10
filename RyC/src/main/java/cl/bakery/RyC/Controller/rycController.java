package cl.bakery.RyC.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.bakery.RyC.Assembler.rycModelAssembler;
import cl.bakery.RyC.Model.ryc;
import cl.bakery.RyC.Services.rycServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/RyC")
public class rycController {

    @Autowired
    private rycServices rycservice;

    @Autowired
    private rycModelAssembler assembler;

    // 🟢 LISTAR TODAS LAS RYC
    @GetMapping
    @Operation(summary = "RYC", description = "Lista todas las reseñas y comentarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista generada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ryc.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron registros")
    })
    public ResponseEntity<?> ListarTodo() {
        List<ryc> lista = rycservice.BuscarTodoRyc();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran registros de reseñas");
        } else {
            return ResponseEntity.ok(assembler.toCollectionModel(lista));
        }
    }

    // 🟡 BUSCAR POR ID
    @GetMapping("/{ID_RESENA}")
    @Operation(summary = "BUSCAR RYC", description = "Obtiene una reseña o comentario por su ID")
    @Parameters(value = {
        @Parameter(name = "ID_RESENA", description = "ID de la reseña a buscar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ryc.class))),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<?> BuscarRyc(@PathVariable Long ID_RESENA) {
        try {
            ryc encontrado = rycservice.BuscarUnRyc(ID_RESENA);
            return ResponseEntity.ok(assembler.toModel(encontrado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la reseña");
        }
    }

    // 🟣 CREAR UNA NUEVA RYC
    @PostMapping
    @Operation(summary = "CREAR RYC", description = "Registra una nueva reseña o comentario",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la reseña a registrar",
            required = true,
            content = @Content(schema = @Schema(implementation = ryc.class))
        ))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña registrada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ryc.class))),
        @ApiResponse(responseCode = "500", description = "Error al registrar la reseña")
    })
    public ResponseEntity<?> GuardarRyc(@RequestBody ryc nuevaRyc) {
        try {
            ryc guardada = rycservice.GuardarRyc(nuevaRyc);
            return ResponseEntity.ok(assembler.toModel(guardada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo registrar la reseña");
        }
    }
}
