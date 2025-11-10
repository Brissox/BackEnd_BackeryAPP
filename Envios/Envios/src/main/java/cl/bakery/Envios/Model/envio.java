package cl.bakery.Envios.Model;

import java.time.LocalDateTime;

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
@Data
@Table(name="ENVIOS")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="Contiene la informacion relacionada con el proceso de envio de productos")

public class envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENVIO")
    @Schema(description = "Identificador del envío, se genera automáticamente")
    private Long id_envio;

    @Column(name = "ID_PEDIDO", nullable = false)
    @Schema(description = "Identificador del pedido asociado", example = "1")
    private Long id_pedido;

    @Column(name = "DIRECCION_ENVIO", nullable = false, length = 200)
    @Schema(description = "Dirección donde se entregará el pedido", example = "Av. Las Condes 1234, Santiago")
    private String direccion_envio;

    @Column(name = "FECHA_ENVIO", nullable = false)
    @Schema(description = "Fecha en que se envía el pedido", example = "2025-06-24T15:30:00")
    private LocalDateTime fecha_envio;

    @Column(name = "FECHA_ENTREGA")
    @Schema(description = "Fecha estimada o real de entrega del pedido", example = "2025-06-26T18:00:00")
    private LocalDateTime fecha_entrega;
}
