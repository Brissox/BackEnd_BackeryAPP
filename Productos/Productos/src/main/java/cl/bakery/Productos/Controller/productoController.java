
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

@RestController
@RequestMapping("/Productos") // <-- Solo cambiamos la URL base
public class productoController {

    @Autowired
    private productoServices productoService;

    @Autowired
    private productoModelAssembler assembler;

    // LISTAR TODOS LOS PRODUCTOS
    @GetMapping
    public ResponseEntity<?> ListarProductos() {
        List<producto> productos = productoService.BuscarTodoProducto();
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran datos");
        }
        return ResponseEntity.ok(assembler.toCollectionModel(productos));
    }

    // BUSCAR UN PRODUCTO POR ID
    @GetMapping("/{ID_PRODUCTO}")
    public ResponseEntity<?> BuscarProducto(@PathVariable Long ID_PRODUCTO) {
        try {
            producto prod = productoService.BuscarUnProducto(ID_PRODUCTO);
            return ResponseEntity.ok(assembler.toModel(prod));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra Producto");
        }
    }

    // CREAR UN NUEVO PRODUCTO
    @PostMapping
    public ResponseEntity<?> GuardarProducto(@RequestBody producto productoGuardar) {
        try {
            producto prod = productoService.GuardarProducto(productoGuardar);
            return ResponseEntity.ok(assembler.toModel(prod));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede registrar el Producto");
        }
    }

    // ACTUALIZAR PRODUCTO POR ID
    @PutMapping("/{ID_PRODUCTO}")
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

    // ELIMINAR PRODUCTO POR ID
    @DeleteMapping("/{ID_PRODUCTO}")
    public ResponseEntity<String> EliminarProducto(@PathVariable Long ID_PRODUCTO) {
        try {
            productoService.EliminarProducto(ID_PRODUCTO);
            return ResponseEntity.status(HttpStatus.OK).body("Producto eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no registrado");
        }
    }

    // FILTRAR PRODUCTOS POR CATEGORIA
    @GetMapping("/Categoria/{categoria}")
    public ResponseEntity<?> FiltrarPorCategoria(@PathVariable String categoria) {
        List<producto> productos = productoService.BuscarPorCategoria(categoria);
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay productos en esta categoría");
        }
        return ResponseEntity.ok(assembler.toCollectionModel(productos));
    }

    @PatchMapping("/descontar")
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
