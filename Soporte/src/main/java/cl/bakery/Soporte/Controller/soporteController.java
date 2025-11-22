package cl.bakery.Soporte.Controller;




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

import cl.bakery.Soporte.Assembler.soporteModelAssembler;
import cl.bakery.Soporte.Model.soporte;
import cl.bakery.Soporte.Services.soporteServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;




@RestController
@RequestMapping("/api/v1/Soporte")

public class soporteController {

@Autowired
    private  soporteServices soporteservices;
@Autowired
    private soporteModelAssembler assambler;
//ENDPOINT PARA listar todos los RYC
    @GetMapping
    @Operation(summary = "ENDPOINT QUE LISTA TODOS LOS SOPORTE", description = "Operacion que lista todos los soporte")
    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se listaron correctamente los soporte", content = @Content(mediaType = "application/json", schema = @Schema(implementation = soporte.class))),
        @ApiResponse(responseCode = "404", description = "No se encontro ningun soporte", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran Datos")))

    })
    public ResponseEntity<?> ListarSoportes(){
        List<soporte> soportes = soporteservices.BuscarTodoSoporte();
        if (soportes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran datos");
        } else {
            return ResponseEntity.ok(assambler.toCollectionModel(soportes));
        }
    }



//ENDPOINT PARA listar un pago por id
    @GetMapping("/{ID_SOPORTE}")
    @Operation(summary = "ENDPOINT QUE LISTA UNA SOPORTE", description = "Operacion que lista un soporte")
    @Parameters (value = {
        @Parameter (name="ID_SOPORTE", description= "ID del soporte que se buscara", in = ParameterIn.PATH, required= true)

    })
    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se lista correctamente el soporte ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = soporte.class))),
        @ApiResponse(responseCode = "404", description = "No se encontro ningun soporte", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran Datos")))
    })

    public ResponseEntity<?> BuscarUnSoporte(@PathVariable Long ID_SOPORTE){

        try {
            soporte soporteBuscado = soporteservices.BuscarUnSoporte(ID_SOPORTE);
            return ResponseEntity.ok(assambler.toModel(soporteBuscado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra soporte");
        }
        
    }


//ENDPOINT PARA agregar un Pago
    @PostMapping
    @Operation(summary = "ENDPOINT QUE REGISTRA UN SOPORTE", description = "ENDPOINT QUE REGISTRA UN SOPORTE",requestBody= @io.swagger.v3.oas.annotations.parameters.RequestBody(description="Soporte QUE SE VA A REGISTRAR", required = true, content = @Content(schema = @Schema(implementation = soporte.class))))
    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se registro correctamente el soporte", content = @Content(mediaType = "application/json", schema = @Schema(implementation = soporte.class))),
        @ApiResponse(responseCode = "500", description = "Indica que no se logro registrar el soporte", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se puede registrar el soporte")))
    })

    public ResponseEntity<?> GuardarSoporte(@RequestBody soporte soporteGuardar){
    try {
            soporte soporteRegistrar = soporteservices.GuardarSoporte(soporteGuardar);
            return ResponseEntity.ok(assambler.toModel(soporteRegistrar));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede registrar el Soporte");
    }
    }


    @GetMapping("/User/{idUsuario}")
    public ResponseEntity<?> buscarCarPorUsuario(@PathVariable Long idUsuario) {
        try {
            List<soporte> rycs = soporteservices.buscarPorUsuario(idUsuario);
            return ResponseEntity.ok(rycs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
    }
    


/* 
//ENDPOINT PARA editar un pago segun id
    @PutMapping("/{ID_SOPORTE}") //SOLO PERMITE ACTUALIZAR ESCRIBIENDO TODOS LOS DATOS
    
    @Operation(summary = "ENDPOINT QUE EDITA UN SOPORTE", description = "ENDPOINT QUE EDITA UN SOPORTE", requestBody=@io.swagger.v3.oas.annotations.parameters.RequestBody(description="RYC QUE SE VA A EDITAR", required = true, content = @Content(schema = @Schema(implementation = ryc.class))))
    @Parameters (value = {
        @Parameter (name="ID_SOPORTE", description= "ID del ryc que se editara", in = ParameterIn.PATH, required= true)})

    @ApiResponses (value = {
        @ApiResponse(responseCode = "200", description = "Se edito correctamente el soporte", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ryc.class))),
        @ApiResponse(responseCode = "500", description = "El soporte no esta registrado", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se puede registrar el ryc")))
    })


    public ResponseEntity<?> ActualizarRYC(@PathVariable Long ID_RESENA, @RequestBody ryc rycActualizar){
        try {
            ryc rycActualizado = soporteservices.BuscarUnRYC(ID_RESENA);
            rycActualizado.setCalificacion(rycActualizar.getCalificacion());
            rycActualizado.setComentario(rycActualizar.getComentario());
            rycActualizado.setResena(rycActualizar.getResena());
            rycActualizado.setId_producto(rycActualizar.getId_producto());
            soporteservices.GuardarRYC(rycActualizado);
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



 