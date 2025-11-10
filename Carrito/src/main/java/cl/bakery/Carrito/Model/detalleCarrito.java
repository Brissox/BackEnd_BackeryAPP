package cl.bakery.Carrito.Model;

import java.math.BigDecimal;

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
@Table(name="detalleCarrito")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Todos los productos registrados en la empresa")

public class detalleCarrito {

    @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_CARRITO")
    @Schema(description = "Identificador único del detalle del carrito", example = "1")
    private Long idDetalleCarrito;

    @Column(name = "ID_CARRITO", nullable = false)
    @Schema(description = "Identificador del carrito al que pertenece este detalle", example = "10")
    private Long idCarrito;

    @Column(name = "ID_PRODUCTO", nullable = false)
    @Schema(description = "Identificador del producto asociado al carrito", example = "501")
    private Long idProducto;

    @Column(name = "CANTIDAD", nullable = false)
    @Schema(description = "Cantidad de productos agregados al carrito", example = "3")
    private Integer cantidad;

    @Column(name = "PRECIO_UNITARIO", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Precio unitario del producto", example = "4990.00")
    private BigDecimal precioUnitario;

    @Column(name = "SUBTOTAL", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Subtotal del producto (cantidad × precio unitario)", example = "14970.00")
    private BigDecimal subtotal;

}
