package cl.bakery.Carrito.Model;

import java.time.LocalDateTime;
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
@Table(name="CARRITO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Todos los productos registrados en la empresa")

public class carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARRITO")
    @Schema(description = "Identificador del carrito de compras", example = "1")
    private Long id_carrito;

    @Column(name = "ID_USUARIO", nullable = false)
    @Schema(description = "Identificador del usuario propietario del carrito", example = "5")
    private Long idUsuario;

    @Column(name = "FECHA_CREACION", nullable = false)
    @Schema(description = "Fecha en que se creó el carrito", example = "2025-11-07")
    private LocalDateTime fecha_creacion;

    @Column(name = "TOTAL", nullable = false, precision = 10)
    @Schema(description = "Monto total acumulado en el carrito", example = "45990")
    private Long total;

    @Column(name = "ESTADO", nullable = false, length = 1)
    @Schema(description = "Estado del carrito (A=Activo / I=Inactivo / P=Pagado)", example = "A")
    private String estado;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Schema(description = "Lista de productos agregados al carrito")
    private List<detalleCarrito> detalles;

    
}