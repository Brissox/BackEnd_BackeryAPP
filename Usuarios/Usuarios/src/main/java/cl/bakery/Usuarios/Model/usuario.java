package cl.bakery.Usuarios.Model;

import java.sql.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//SE CREA LA ENTIDAD USUARIO CAMBIANDO TODO LO QUE TENIA BRISSO ANTES

@Entity
@Table(name = "USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa a los usuarios registrados en la empresa")
public class usuario {

    @Id
    @Column(name = "ID_USUARIO", length = 128)
    @Schema(description = "UID proporcionado por Firebase", example = "hYtU92lKp1sXq9zT2N7Q8W")
    private String idUsuario; // UID de Firebase

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "USUARIO", nullable = false, length = 30, unique = true)
    private String usuario;

    @Column(name = "CORREO", nullable = false, length = 100, unique = true)
    private String correo;

    @Column(name = "CELULAR", length = 15)
    private String celular;

    @Column(name = "GENERO", length = 15)
    private String genero;

    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    @Column(name = "PAIS", length = 30)
    private String pais;

    @Column(name = "CIUDAD", length = 30)
    private String ciudad;

    @Column(name = "DIRECCION", length = 100)
    private String direccion;

    @Column(name = "CODIGO_DESC", length = 30)
    private String codigoDesc;

    @Enumerated(EnumType.STRING)//ENUMTYPE DECLARA EL ROL COMO LOS DATOS QUE SE DEFINIERON EN ROL.JAVA
    @Column(name = "ROL", nullable = false, length = 20)
    private Rol rol;

    @Column(name = "ESTADO", nullable = false, length = 1)
    private String estado = "A";
}
