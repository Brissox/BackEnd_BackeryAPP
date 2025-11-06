package cl.bakery.Soporte.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Soporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Todos los productos registrados en la empresa")

public class soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SOPORTE")
    private Long idSoporte;

    @Column(name = "ID_USUARIO", nullable = false)
    private Long idUsuario;

    @Column(name = "ASUNTO", nullable = false, length = 100)
    private String asunto;

    @Column(name = "MENSAJE", nullable = false, length = 500)
    private String mensaje;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado; // (abierto, en proceso, cerrado)
}