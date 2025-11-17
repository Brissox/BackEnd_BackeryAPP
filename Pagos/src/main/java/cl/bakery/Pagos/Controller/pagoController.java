package cl.bakery.Pagos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private pagoServices pagoservices;
@Autowired
    private pagoModelAssembler assambler;
//ENDPOINT PARA listar todos los pagos
    @GetMapping
    @Operation(summary = "ENDPOINT QUE LISTA TODOS LOS PAGOS", description = "Operacion que lista todos los Pagos")
    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se listaron correctamente los Pagos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = pago.class))),
        @ApiResponse(responseCode = "404", description = "No se encontro ningun pago", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran Datos")))

    })
    public ResponseEntity<?> ListarPagos(){
        List<pago> pagos = pagoservices.BuscarTodoPago();
        if (pagos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran datos");
        } else {
            return ResponseEntity.ok(assambler.toCollectionModel(pagos));
        }
    }



//ENDPOINT PARA listar un pago por id
    @GetMapping("/{ID_PAGO}")
    @Operation(summary = "ENDPOINT QUE LISTA UN PAGO", description = "Operacion que lista un pago")
    @Parameters (value = {
        @Parameter (name="ID_PAGO", description= "ID del pago que se buscara", in = ParameterIn.PATH, required= true)

    })
    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se lista correctamente el pago ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = pago.class))),
        @ApiResponse(responseCode = "404", description = "No se encontro ningun pago", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran Datos")))
    })

    public ResponseEntity<?> BuscarUnPago(@PathVariable Long ID_PAGO){

        try {
            pago pagoBuscado = pagoservices.BuscarUnPago(ID_PAGO);
            return ResponseEntity.ok(assambler.toModel(pagoBuscado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra Pago");
        }
        
    }


//ENDPOINT PARA agregar un Pago
    @PostMapping
    @Operation(summary = "ENDPOINT QUE REGISTRA UN PAGO", description = "ENDPOINT QUE REGISTRA UN PAGO",requestBody= @io.swagger.v3.oas.annotations.parameters.RequestBody(description="PAGO QUE SE VA A REGISTRAR", required = true, content = @Content(schema = @Schema(implementation = pago.class))))
    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se registro correctamente el pago", content = @Content(mediaType = "application/json", schema = @Schema(implementation = pago.class))),
        @ApiResponse(responseCode = "500", description = "Indica que no se logro registrar el pago", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se puede registrar el pago")))
    })

    public ResponseEntity<?> GuardarPago(@RequestBody pago pagoGuardar){
    try {
            pago pagoRegistrar = pagoservices.GuardarPago(pagoGuardar);
            return ResponseEntity.ok(assambler.toModel(pagoRegistrar));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede registrar el Pago");
    }
    }


     @GetMapping("/Pedido/{idPedido}")
    public ResponseEntity<?> buscarCarPorPedido(@PathVariable Long idPedido) {
        try {
            List<pago> pagos = pagoservices.buscarPorPedido(idPedido);
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagos no encontrado");
        }
    }
    


/*
//ENDPOINT PARA editar un pago segun id
    @PutMapping("/{ID_PAGO}") //SOLO PERMITE ACTUALIZAR ESCRIBIENDO TODOS LOS DATOS
    
    @Operation(summary = "ENDPOINT QUE EDITA UN PAGO", description = "ENDPOINT QUE EDITA UN PAGO", requestBody=@io.swagger.v3.oas.annotations.parameters.RequestBody(description="PAGO QUE SE VA A EDITAR", required = true, content = @Content(schema = @Schema(implementation = pago.class))))
    @Parameters (value = {
        @Parameter (name="ID_PAGO", description= "ID del pago que se editara", in = ParameterIn.PATH, required= true)})

    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se edito correctamente el pago", content = @Content(mediaType = "application/json", schema = @Schema(implementation = pago.class))),
        @ApiResponse(responseCode = "500", description = "El pago no esta registrado", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se puede registrar el pago")))
    })


    public ResponseEntity<?> ActualizarPagos(@PathVariable Long ID_PAGO, @RequestBody pago pagoActualizar){
        try {
            pago pagoActualizado = pagoservices.BuscarUnPago(ID_PAGO);
            pagoActualizado.setNombre(usuarioActualizar.getNombre());
            pagoActualizado.setId_rol(usuarioActualizar.getId_rol());
            pagoActualizado.setApellido_paterno(usuarioActualizar.getApellido_paterno());
            pagoActualizado.setApellido_materno(usuarioActualizar.getApellido_materno());
            pagoActualizado.setFecha_nacimiento(usuarioActualizar.getFecha_nacimiento());
            pagoActualizado.setCorreo(usuarioActualizar.getCorreo());
            pagoActualizado.setDireccion(usuarioActualizar.getDireccion());
            pagoActualizado.setTelefono(usuarioActualizar.getTelefono());
            pagoActualizado.setContrasena(usuarioActualizar.getContrasena());
            pagoActualizado.setRun(usuarioActualizar.getRun());
            pagoActualizado.setDv(usuarioActualizar.getDv());
            pagoActualizado.setEstado(usuarioActualizar.getEstado());

            pagoservices.GuardarPago(pagoActualizado);
            return ResponseEntity.ok(assambler.toModel(pagoActualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no esta registrado");
        }
    }
    
    /*
        @DeleteMapping("/{ID_PAGO}")
        public ResponseEntity<String> EliminarUsuario(@PathVariable Long ID_PAGO){
            try {
                usuario usuarioBuscado = usuarioservices.BuscarUnUsuario(ID_PAGO);
                usuarioservices.EliminarUsuario(ID_PAGO);
                return ResponseEntity.status(HttpStatus.OK).body("Se elimina Usuario");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no esta registrado");
            }
        }
            */

}
