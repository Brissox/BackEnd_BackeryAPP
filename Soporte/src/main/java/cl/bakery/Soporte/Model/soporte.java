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
    @Column(name = "ID_SOPORTE", nullable = false)
    @Schema(description = "Identificador único del soporte", example = "1")
    private Long id_soporte;


    @Column(name = "ID_USUARIO", nullable = false)
    @Schema(description = "Identificador del usuario que creó el ticket", example = "1001")
    private Long idUsuario;

    @Column(name = "ASUNTO", nullable = false, length = 100)
    @Schema(description = "Asunto o título del ticket de soporte", example = "Problema con el pago")
    private String asunto;

    @Column(name = "MENSAJE", nullable = false, length = 500)
    @Schema(description = "Mensaje detallando el problema o solicitud", example = "No puedo completar la compra con mi tarjeta")
    private String mensaje;

    @Column(name = "ESTADO", nullable = false, length = 20)
    @Schema(description = "Estado actual del ticket (abierto, en proceso, cerrado)", example = "abierto")
    private String estado;
}