
package cl.bakery.Productos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.bakery.Productos.Assembler.productoModelAssembler;
import cl.bakery.Productos.Model.producto;
import cl.bakery.Productos.Services.productoServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/Productos") // <-- Solo cambiamos la URL base
public class productoController {

    @Autowired
    private productoServices productoService;

    @Autowired
    private productoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "LISTA TODOS LOS PRODUCTOS", description = "Devuelve todos los productos registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = producto.class))),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran datos")))
    })
    public ResponseEntity<?> ListarProductos() {
        List<producto> productos = productoService.BuscarTodoProducto();
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran datos");
        }
        return ResponseEntity.ok(assembler.toCollectionModel(productos));
    }

    @GetMapping("/{ID_PRODUCTO}")
    @Operation(summary = "BUSCA UN PRODUCTO POR SU ID", description = "Permite obtener un producto específico mediante su ID")
    @Parameters(value = {
            @Parameter(name = "ID_PRODUCTO", description = "ID del producto que se desea consultar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = producto.class))),
            @ApiResponse(responseCode = "404", description = "No se encuentra Producto", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentra Producto")))
    })
    public ResponseEntity<?> BuscarProducto(@PathVariable Long ID_PRODUCTO) {
        try {
            producto prod = productoService.BuscarUnProducto(ID_PRODUCTO);
            return ResponseEntity.ok(assembler.toModel(prod));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra Producto");
        }
    }

    @PostMapping
    @Operation(summary = "REGISTRA UN NUEVO PRODUCTO", description = "Permite crear un nuevo producto enviando su información en el cuerpo de la solicitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto registrado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = producto.class))),
            @ApiResponse(responseCode = "409", description = "No se puede registrar el Producto", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se puede registrar el Producto")))
    })
    public ResponseEntity<?> GuardarProducto(@RequestBody producto productoGuardar) {
        try {
            producto prod = productoService.GuardarProducto(productoGuardar);
            return ResponseEntity.ok(assembler.toModel(prod));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede registrar el Producto");
        }
    }

    @PutMapping("/{ID_PRODUCTO}")
    @Operation(summary = "Actualizar un producto por ID", description = "Actualiza completamente un producto existente mediante su ID. Se reemplazan todos los campos del producto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<?> ActualizarProducto(@PathVariable Long ID_PRODUCTO,
            @RequestBody producto productoActualizar) {
        try {
            producto prod = productoService.BuscarUnProducto(ID_PRODUCTO);
            prod.setNombre(productoActualizar.getNombre());
            prod.setPrecio(productoActualizar.getPrecio());
            prod.setSku(productoActualizar.getSku());
            prod.setDescripcion(productoActualizar.getDescripcion());
            prod.setEstado(productoActualizar.getEstado());
            prod.setStock(productoActualizar.getStock()); // <--- esto
            productoService.GuardarProducto(prod);
            return ResponseEntity.ok(assembler.toModel(prod));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no registrado");
        }
    }

    @DeleteMapping("/{ID_PRODUCTO}")
    @Operation(summary = "Eliminar un producto por ID", description = "Elimina un producto existente utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<String> EliminarProducto(@PathVariable Long ID_PRODUCTO) {
        try {
            productoService.EliminarProducto(ID_PRODUCTO);
            return ResponseEntity.status(HttpStatus.OK).body("Producto eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no registrado");
        }
    }

    @GetMapping("/Categoria/{categoria}")
    @Operation(summary = "Filtrar productos por categoría", description = "Obtiene todos los productos que pertenecen a la categoría especificada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos encontrados", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = producto.class)))),
            @ApiResponse(responseCode = "404", description = "No existen productos en esta categoría")
    })
    public ResponseEntity<?> FiltrarPorCategoria(@PathVariable String categoria) {
        List<producto> productos = productoService.BuscarPorCategoria(categoria);
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay productos en esta categoría");
        }
        return ResponseEntity.ok(assembler.toCollectionModel(productos));
    }

    @PatchMapping("/descontar")
    @Operation(summary = "Descontar stock de un producto", description = "Descuenta una cantidad específica del stock disponible. Si la cantidad solicitada es mayor al stock actual, devuelve error.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock descontado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "409", description = "Stock insuficiente para realizar la operación")
    })
    public ResponseEntity<?> descontarStock(
            @RequestParam Long idProducto,
            @RequestParam int cantidad) {

        try {
            producto prod = productoService.BuscarUnProducto(idProducto);

            if (prod.getStock() < cantidad) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Stock insuficiente");
            }

            prod.setStock(prod.getStock() - cantidad);
            productoService.GuardarProducto(prod);

            return ResponseEntity.ok("Stock descontado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto no encontrado");
        }
    }

}
