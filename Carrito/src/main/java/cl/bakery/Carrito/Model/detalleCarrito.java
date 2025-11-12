package cl.bakery.Carrito.Model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="detalleCarrito")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Todos los productos registrados en el Carrito")

public class detalleCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_CARRITO")
    @Schema(description = "Identificador único del detalle del carrito", example = "1")
    private Long idDetalleCarrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CARRITO", nullable = false)
    @JsonBackReference
    @Schema(description = "Carrito al que pertenece este detalle")
    private carrito carrito;

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
