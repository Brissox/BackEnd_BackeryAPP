package cl.bakery.Carrito.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "CARRITO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Todos los productos registrados en la empresa")

public class carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARRITO")
    private Long idCarrito;

    @Column(name = "ID_USUARIO", nullable = false)
    private Long idUsuario;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "TOTAL", nullable = false)
    private long total;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;

}