package cl.bakery.Descuentos.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bakery.Descuentos.DTO.DescuentoDTO;
import cl.bakery.Descuentos.DTO.UsuarioRegistroDescuentoDTO;
import cl.bakery.Descuentos.Model.descuento;
import cl.bakery.Descuentos.Services.DescuentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/descuentos")
public class DescuentoController {

    @Autowired
    private DescuentoService service;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar descuento por ID", description = "Obtiene los datos de un descuento específico a partir de su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descuento encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = descuento.class))),
            @ApiResponse(responseCode = "404", description = "Descuento no encontrado")
    })
    public ResponseEntity<descuento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping("/asignar")
    @Operation(summary = "Asignar descuentos según reglas", description = "Evalúa reglas predefinidas y asigna automáticamente descuentos a un usuario, según la información proporcionada en el DTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descuentos asignados correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos en la solicitud")
    })
    public ResponseEntity<?> asignarDescuentos(@RequestBody UsuarioRegistroDescuentoDTO dto) {
        service.asignarDescuentosPorReglas(dto);
        return ResponseEntity.ok("Descuentos asignados");
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Obtener descuentos por usuario", description = "Retorna la lista de descuentos asignados a un usuario específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de descuentos obtenida correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DescuentoDTO.class)))),
            @ApiResponse(responseCode = "404", description = "El usuario no posee descuentos o no existe")
    })
    public List<DescuentoDTO> obtenerDescuentosPorUsuario(@PathVariable Long idUsuario) {
        return service.obtenerDescuentosPorUsuario(idUsuario)
                .stream()
                .map(d -> new DescuentoDTO(d.getId(), d.getCodigo(), d.getDescripcion(), d.getPorcentaje()))
                .collect(Collectors.toList());
    }

}