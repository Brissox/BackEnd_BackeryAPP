package cl.bakery.Dcarrito.Model;

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
@Table(name="DETALLE_CARRITO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Todos los productos registrados en la empresa")

public class dcarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_CARRITO")
    private Long idDetalleCarrito;

    @Column(name = "ID_CARRITO", nullable = false)
    private Long idCarrito;

    @Column(name = "ID_PRODUCTO", nullable = false)
    private Long idProducto;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @Column(name = "PRECIO_UNITARIO", nullable = false)
    private long precioUnitario;

    @Column(name = "SUBTOTAL", nullable = false)
    private BigDecimal subtotal;

}