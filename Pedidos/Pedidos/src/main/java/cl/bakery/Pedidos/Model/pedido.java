package cl.bakery.Pedidos.Model;

import java.time.LocalDate;

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
@Table(name = "PEDIDOS", schema = "ADMIN") // 👈 IMPORTANTE: fuerza el esquema correcto
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Todos los pedidos registrados en la empresa")
public class pedido { // 👈 Nombre de clase con mayúscula, buena práctica en Java

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO")
    @Schema(description = "Identificador del pedido", example = "1")
    private Long idPedido;

    @Column(name = "FECHA", nullable = false)
    @Schema(description = "Fecha en que se realizó el pedido", example = "2025-11-07")
    private LocalDate fecha;

    @Column(name = "CLIENTE", nullable = false, length = 100)
    @Schema(description = "Nombre o identificador del cliente que realizó el pedido", example = "Juan Pérez")
    private String cliente;

    @Column(name = "VENDEDOR", nullable = false, length = 100)
    @Schema(description = "Nombre o identificador del vendedor asociado al pedido", example = "María González")
    private String vendedor;

    @Column(name = "MONTO", nullable = false, precision = 10)
    @Schema(description = "Monto total del pedido en moneda local", example = "12500")
    private Long monto;
}
