package cl.bakery.Usuarios.Controller;

import cl.bakery.Usuarios.Model.usuario;
import cl.bakery.Usuarios.Services.usuarioServices;
import cl.bakery.Usuarios.Assembler.usuarioModelAssembler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/usuarios")
public class usuarioController {

    @Autowired
    private usuarioServices usuarioservices;

    @Autowired
    private usuarioModelAssembler assembler;

    // NO SE CAMBIA MUCHO AQUI
    @GetMapping
    @Operation(summary = "Lista todos los usuarios")
    public ResponseEntity<?> listarUsuarios() {
        List<usuario> usuarios = usuarioservices.BuscarTodoUsuario();
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran datos");
        } else {
            return ResponseEntity.ok(assembler.toCollectionModel(usuarios));
        }
    }

    // NO SE CAMBIA MUCHO AQUI
    @GetMapping("/{uid}")
    @Operation(summary = "Obtiene un usuario por UID")
    public ResponseEntity<?> buscarUsuario(@PathVariable String uid) {
        try {
            usuario usuarioBuscado = usuarioservices.BuscarUnUsuario(uid);
            return ResponseEntity.ok(assembler.toModel(usuarioBuscado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra Usuario");
        }
    }

    @PostMapping("/registrar")
    @Operation(summary = "Registra o actualiza un usuario autenticado con Firebase", description = "Verifica el token enviado desde Firebase y guarda el usuario en la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = usuario.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido o expirado", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> registrarUsuario(
            @RequestHeader("Authorization") String tokenHeader,
            @RequestBody usuario usuarioGuardar) {
        try {
            // ESTA SHIT RECIBE EL TOKEN QUE MANDA EL FRONT
            String idToken = tokenHeader.replace("Bearer ", "");
            // VERIFICA EL TOKEN
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            // DEFINE EL UID Y EL EMAIL CON EL TOKEN "DECODIFICADO"
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            // GUARDA UID Y CORREO DESDE FIREBASE
            usuarioGuardar.setIdUsuario(uid);
            usuarioGuardar.setCorreo(email);

            // GUARDA CON LA FUNCION DE JPA A LA BASE DE DATOS
            usuario usuarioRegistrado = usuarioservices.GuardarUsuario(usuarioGuardar);

            // DEVUELVE EL USUARIO
            return ResponseEntity.ok(assembler.toModel(usuarioRegistrado));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido o error al registrar: " + e.getMessage());
        }
    }

    // NO SE CAMBIA MUCHO AQUI
    @PutMapping("/{uid}")
    @Operation(summary = "Actualiza los datos de un usuario por UID")
    public ResponseEntity<?> actualizarUsuario(@PathVariable String uid, @RequestBody usuario usuarioActualizar) {
        try {
            usuario usuarioActualizado = usuarioservices.BuscarUnUsuario(uid);
            usuarioActualizado.setNombre(usuarioActualizar.getNombre());
            usuarioActualizado.setDireccion(usuarioActualizar.getDireccion());
            usuarioActualizado.setCelular(usuarioActualizar.getCelular());
            usuarioActualizado.setEstado(usuarioActualizar.getEstado());
            usuarioservices.GuardarUsuario(usuarioActualizado);
            return ResponseEntity.ok(assembler.toModel(usuarioActualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no está registrado");
        }
    }
}
