package cl.bakery.Dcarrito.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.bakery.Dcarrito.Model.dcarrito;
import cl.bakery.Dcarrito.Services.dcarritoService;
import cl.bakery.Dcarrito.Assembler.dcarritoModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/Dcarrito")
public class dcarritoController {

    @Autowired
    private dcarritoService dcarritoService;

    @Autowired
    private dcarritoModelAssembler assembler;

    // 🔹 Listar todos
    @GetMapping
    public ResponseEntity<?> ListarTodo() {
        List<dcarrito> lista = dcarritoService.BuscarTodo();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay detalles de carrito registrados");
        }
        List<EntityModel<dcarrito>> modelos = lista.stream().map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(modelos,
                linkTo(methodOn(dcarritoController.class).ListarTodo()).withSelfRel()));
    }

    // 🔹 Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarDCarrito(@PathVariable Long id) {
        dcarrito detalle = dcarritoService.BuscarUno(id);
        if (detalle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el detalle de carrito");
        }
        return ResponseEntity.ok(assembler.toModel(detalle));
    }

    // 🔹 Guardar nuevo
    @PostMapping
    public ResponseEntity<?> Guardar(@RequestBody dcarrito dcarrito) {
        try {
            dcarrito guardado = dcarritoService.Guardar(dcarrito);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(guardado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al guardar el detalle de carrito");
        }
    }

    // 🔹 Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> Actualizar(@PathVariable Long id, @RequestBody dcarrito detalle) {
        dcarrito existente = dcarritoService.BuscarUno(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Detalle no encontrado");
        }
        existente.setIdCarrito(detalle.getIdCarrito());
        existente.setIdProducto(detalle.getIdProducto());
        existente.setCantidad(detalle.getCantidad());
        existente.setPrecioUnitario(detalle.getPrecioUnitario());
        existente.setSubtotal(detalle.getSubtotal());

        dcarrito actualizado = dcarritoService.Guardar(existente);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    // 🔹 Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id) {
        try {
            dcarritoService.Eliminar(id);
            return ResponseEntity.ok("Detalle eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar detalle");
        }
    }
}
