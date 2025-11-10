package cl.bakery.Pagos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.bakery.Pagos.Assembler.pagoModelAssembler;
import cl.bakery.Pagos.Model.pago;
import cl.bakery.Pagos.Services.pagoServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/Pagos")
public class pagoController {

    @Autowired
    private pagoServices pagoservice;

    @Autowired
    private pagoModelAssembler assembler;

    // ENDPOINT PARA LISTAR TODOS LOS PAGOS
    @GetMapping
    @Operation(summary = "PAGOS", description = "Operación que lista todos los pagos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagos listados correctamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = pago.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pagos", 
                     content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran datos")))
    })
    public ResponseEntity<?> ListarTodo() {
        List<pago> pagos = pagoservice.BuscarTodoPagos();
        if (pagos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran datos");
        } else {
            return ResponseEntity.ok(assembler.toCollectionModel(pagos));
        }
    }

    // ENDPOINT PARA BUSCAR UN PAGO POR ID
    @GetMapping("/{ID_PAGO}")
    @Operation(summary = "PAGO", description = "Operación que lista un pago por su ID")
    @Parameters(value = {
        @Parameter(name = "ID_PAGO", description = "ID del pago a buscar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = pago.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró el pago", 
                     content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentra el pago")))
    })
    public ResponseEntity<?> BuscarPago(@PathVariable Long ID_PAGO) {
        pago pagoBuscado = pagoservice.BuscarPago(ID_PAGO);
        if (pagoBuscado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra el pago");
        }
        return ResponseEntity.ok(assembler.toModel(pagoBuscado));
    }

    // ENDPOINT PARA REGISTRAR UN PAGO
    @PostMapping
    @Operation(summary = "Registrar un pago", description = "Registra un nuevo pago", 
               requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                   description = "Pago a registrar", required = true, 
                   content = @Content(schema = @Schema(implementation = pago.class))
               ))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago registrado correctamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = pago.class))),
        @ApiResponse(responseCode = "500", description = "No se pudo registrar el pago", 
                     content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se puede registrar el pago")))
    })
    public ResponseEntity<?> GuardarPago(@RequestBody pago pagoGuardar) {
        try {
            pago pagoRegistrado = pagoservice.GuardarPago(pagoGuardar);
            return ResponseEntity.ok(assembler.toModel(pagoRegistrado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede registrar el pago");
        }
    }
}
