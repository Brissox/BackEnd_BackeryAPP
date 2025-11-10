package cl.bakery.Carrito.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bakery.Carrito.Assembler.carritoModelAssembler;
import cl.bakery.Carrito.Model.carrito;
import cl.bakery.Carrito.Services.carritoServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/Carritos")
public class carritoController {

    @Autowired
    private carritoServices carritoService;

    @Autowired
    private carritoModelAssembler assembler;

    // ENDPOINT PARA LISTAR TODOS LOS CARRITOS
    @GetMapping
    @Operation(summary = "CARRITOS", description = "Operación que lista todos los carritos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se listaron correctamente los carritos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = carrito.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún carrito", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran datos")))
    })
    public ResponseEntity<?> listarTodo() {
        List<carrito> carritos = carritoService.BuscarTodoCarritos();
        if (carritos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran datos");
        } else {
            return ResponseEntity.ok(assembler.toCollectionModel(carritos));
        }
    }

    // ENDPOINT PARA BUSCAR UN CARRITO POR ID
    @GetMapping("/{ID_CARRITO}")
    @Operation(summary = "CARRITO", description = "Operación que lista un carrito por ID")
    @Parameters(value = {
        @Parameter(name = "ID_CARRITO", description = "ID del carrito que se buscará", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se listó correctamente el carrito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = carrito.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró el carrito", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentra el carrito")))
    })
    public ResponseEntity<?> buscarCarrito(@PathVariable Long ID_CARRITO) {
        try {
            carrito carritoBuscado = carritoService.BuscarUnCarrito(ID_CARRITO);
            return ResponseEntity.ok(assembler.toModel(carritoBuscado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra el carrito");
        }
    }

    // ENDPOINT PARA REGISTRAR UN CARRITO
    @PostMapping
    @Operation(summary = "REGISTRA UN CARRITO", description = "Endpoint que registra un nuevo carrito",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Carrito que se va a registrar",
            required = true,
            content = @Content(schema = @Schema(implementation = carrito.class))
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se registró correctamente el carrito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = carrito.class))),
        @ApiResponse(responseCode = "500", description = "No se logró registrar el carrito", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se puede registrar el carrito")))
    })
    public ResponseEntity<?> guardarCarrito(@RequestBody carrito carritoGuardar) {
        try {
            carrito carritoRegistrado = carritoService.GuardarCarrito(carritoGuardar);
            return ResponseEntity.ok(assembler.toModel(carritoRegistrado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede registrar el carrito");
        }
    }
}