package cl.bakery.Usuarios.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para que un admin edite todos los campos de un usuario, incluido el rol")
public class EditarUsuarioAdminDTO {
    
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private int telefono;
    private String fechaNacimiento;
    private String pais;
    private String ciudad;
    private String direccion;
    private String estado;

    @Schema(description = "ID del rol asignado al usuario")
    private Long idRol;
}
