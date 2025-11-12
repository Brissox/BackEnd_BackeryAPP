package cl.bakery.Carrito.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bakery.Carrito.Model.carrito;
import cl.bakery.Carrito.Services.carritoServices;
/*
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses; */


@RestController
@RequestMapping("/api/v1/Carritos")
public class carritoController {

    @Autowired
    private carritoServices carritoServices;

    /*Listar todos los carritos*/
    @GetMapping
    public ResponseEntity<?> listarCarritos() {
        List<carrito> carritos = carritoServices.buscarTodos();
        if (carritos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay carritos");
        } else {
            return ResponseEntity.ok(carritos);
        }
    }

    /*Listar un carrito*/
    @GetMapping("/{ID_CARRITO}")
    public ResponseEntity<?> buscarCarrito(@PathVariable Long ID_CARRITO) {
        try {
            carrito carrito = carritoServices.buscarPorId(ID_CARRITO);
            return ResponseEntity.ok(carrito);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado");
        }
    }

     /*Listar un carrito*/
    @GetMapping("/User/{idUsuario}")
    public ResponseEntity<?> buscarCarPorUsuario(@PathVariable Long idUsuario) {
        try {
            List<carrito> carritos = carritoServices.buscarPorUsuario(idUsuario);
            return ResponseEntity.ok(carritos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carritos no encontrado");
        }
    }

    /* Crear nuevo carrito*/
    @PostMapping
    public ResponseEntity<?> guardarCarrito(@RequestBody carrito Carrito) {
        try {
            carrito nuevo = carritoServices.guardarCarrito(Carrito);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo crear el carrito");
        }
    }

    /* Eliminar Carrito*/
    @DeleteMapping("/{ID_CARRITO}") 
        public ResponseEntity<String> eliminarCar(@PathVariable Long ID_CARRITO){
            try {
                carrito carritoBuscado = carritoServices.buscarPorId(ID_CARRITO);
                
                if (carritoBuscado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no está registrado");
                }
                carritoServices.eliminarCar(ID_CARRITO);
                return ResponseEntity.status(HttpStatus.OK).body("Carrito eliminado exitosamente");
            } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
            } 
        }
}

