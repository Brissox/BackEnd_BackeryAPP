package cl.bakery.Pedidos.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PEDIDO_DETALLE")
@Data
public class PedidoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PEDIDO")
    @JsonBackReference
    private Pedido pedido;

    @Column(name = "ID_PRODUCTO", nullable = false)
    private Long idProducto;

    @Column(name = "CANTIDAD", nullable = false)
    private int cantidad;

    @Column(name = "PRECIO_UNITARIO", nullable = false)
    private Double precioUnitario;

    @Column(name = "SUBTOTAL", nullable = false)
    private Double subtotal;
}
