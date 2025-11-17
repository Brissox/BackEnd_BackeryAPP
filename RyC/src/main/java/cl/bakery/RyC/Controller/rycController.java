package cl.bakery.RyC.Controller;

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
@RequestMapping("/api/v1/RYC")

public class rycController {

@Autowired
    private  rycServices rycservices;
@Autowired
    private rycModelAssembler assambler;
//ENDPOINT PARA listar todos los RYC
    @GetMapping
    @Operation(summary = "ENDPOINT QUE LISTA TODOS LOS RYC", description = "Operacion que lista todos los RYC")
    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se listaron correctamente los ryc", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ryc.class))),
        @ApiResponse(responseCode = "404", description = "No se encontro ningun ryc", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran Datos")))

    })
    public ResponseEntity<?> ListarRYC(){
        List<ryc> rycs = rycservices.BuscarTodoRYC();
        if (rycs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran datos");
        } else {
            return ResponseEntity.ok(assambler.toCollectionModel(rycs));
        }
    }



//ENDPOINT PARA listar una resena por id
    @GetMapping("/{ID_RESENA}")
    @Operation(summary = "ENDPOINT QUE LISTA UNA RYC", description = "Operacion que lista un RYC")
    @Parameters (value = {
        @Parameter (name="ID_RESENA", description= "ID del ryc que se buscara", in = ParameterIn.PATH, required= true)

    })
    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se lista correctamente el ryc ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ryc.class))),
        @ApiResponse(responseCode = "404", description = "No se encontro ningun ryc", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran Datos")))
    })

    public ResponseEntity<?> BuscarUnRYC(@PathVariable Long ID_RESENA){

        try {
            ryc rycBuscado = rycservices.BuscarUnRYC(ID_RESENA);
            return ResponseEntity.ok(assambler.toModel(rycBuscado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra Pago");
        }
        
    }


//ENDPOINT PARA agregar una resena
    @PostMapping
    @Operation(summary = "ENDPOINT QUE REGISTRA UN RYC", description = "ENDPOINT QUE REGISTRA UN RYC",requestBody= @io.swagger.v3.oas.annotations.parameters.RequestBody(description="RYC QUE SE VA A REGISTRAR", required = true, content = @Content(schema = @Schema(implementation = ryc.class))))
    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se registro correctamente el ryc", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ryc.class))),
        @ApiResponse(responseCode = "500", description = "Indica que no se logro registrar el ryc", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se puede registrar el ryc")))
    })

    public ResponseEntity<?> GuardarPago(@RequestBody ryc pagoGuardar){
    try {
            ryc rycRegistrar = rycservices.GuardarRYC(pagoGuardar);
            return ResponseEntity.ok(assambler.toModel(rycRegistrar));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede registrar el RYC");
    }
    }


     @GetMapping("/User/{idUsuario}")
    public ResponseEntity<?> buscarCarPorUsuario(@PathVariable Long idUsuario) {
        try {
            List<ryc> rycs = rycservices.buscarPorUsuario(idUsuario);
            return ResponseEntity.ok(rycs);
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
    

    
     @GetMapping("/Producto/{idProducto}")
    public ResponseEntity<?> buscarPorProducto(@PathVariable Long idProducto) {
        try {
            List<ryc> rycs = rycservices.buscarPorProducto(idProducto);
            return ResponseEntity.ok(rycs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }



//ENDPOINT PARA editar un pago segun id
    @PutMapping("/{ID_RESENA}") //SOLO PERMITE ACTUALIZAR ESCRIBIENDO TODOS LOS DATOS
    
    @Operation(summary = "ENDPOINT QUE EDITA UN PAGO", description = "ENDPOINT QUE EDITA UNA RYC", requestBody=@io.swagger.v3.oas.annotations.parameters.RequestBody(description="RYC QUE SE VA A EDITAR", required = true, content = @Content(schema = @Schema(implementation = ryc.class))))
    @Parameters (value = {
        @Parameter (name="ID_RESENA", description= "ID del ryc que se editara", in = ParameterIn.PATH, required= true)})

    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se edito correctamente el ryc", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ryc.class))),
        @ApiResponse(responseCode = "500", description = "El ryc no esta registrado", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se puede registrar el ryc")))
    })


    public ResponseEntity<?> ActualizarRYC(@PathVariable Long ID_RESENA, @RequestBody ryc rycActualizar){
        try {
            ryc rycActualizado = rycservices.BuscarUnRYC(ID_RESENA);
            rycActualizado.setCalificacion(rycActualizar.getCalificacion());
            rycActualizado.setComentario(rycActualizar.getComentario());
            rycActualizado.setResena(rycActualizar.getResena());
            rycActualizado.setId_producto(rycActualizar.getId_producto());
            rycservices.GuardarRYC(rycActualizado);
            return ResponseEntity.ok(assambler.toModel(rycActualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RYC no esta registrado");
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

