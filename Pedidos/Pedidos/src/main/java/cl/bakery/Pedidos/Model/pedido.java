package cl.bakery.Pedidos.Model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

public class pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO")
    @Schema(description = "Identificador del pedido", example = "1")
    private Long id_pedido;

    @Column(name = "ID_USUARIO", nullable = false, length = 100)
    @Schema(description = "Nombre o identificador del cliente que realizó el pedido", example = "Juan Pérez")
    private Long idUsuario;

    @Column(name = "FECHA", nullable = false)
    @Schema(description = "Fecha en que se realizó el pedido", example = "2025-11-07")
    private LocalDate fecha;

    @Column(name = "CANTIDAD_PRODUCTOS", nullable = false)
    @Schema(description = "Cantidad total de productos en el pedido", example = "3")
    private Integer cantidad_productos;

    @Column(name = "TOTAL", nullable = false, precision = 10)
    @Schema(description = "Monto total del pedido en moneda local", example = "12500")
    private Long total;

    @Column(name = "METODO_DE_PAGO", nullable = false, length = 50)
    @Schema(description = "Método de pago utilizado en el pedido", example = "Tarjeta de crédito")
    private String metodo_de_pago;

    @Column(name = "DESCUENTOS", nullable = true, precision = 10)
    @Schema(description = "Descuento aplicado al pedido", example = "500")
    private Long descuentos;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Schema(description = "Lista de productos agregados al pedido")
    private List<detallePedido> detalles;
}


