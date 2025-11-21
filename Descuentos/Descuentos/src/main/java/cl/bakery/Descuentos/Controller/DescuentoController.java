package cl.bakery.Descuentos.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bakery.Descuentos.Model.descuento;
import cl.bakery.Descuentos.Services.DescuentoService;

@RestController
@RequestMapping("/descuentos")
public class DescuentoController {

    private final DescuentoService service;

    public DescuentoController(DescuentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<descuento>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<descuento> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<descuento> crear(@RequestBody descuento Descuento) {
        return ResponseEntity.ok(service.crear(Descuento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<descuento> actualizar(@PathVariable Integer id, @RequestBody descuento Descuento) {
        return ResponseEntity.ok(service.actualizar(id, Descuento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok("Descuento eliminado");
    }
}
