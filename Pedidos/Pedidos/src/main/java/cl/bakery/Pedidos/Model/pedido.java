package cl.bakery.Pedidos.Model;

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
@Table(name="PEDIDOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="Todos los pedidos registrados en la empresa")

public class pedido {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "ID_PEDIDO")
    @Schema(description="identificador de pedido", example= "1")
    private long id_pedido;
    
    @Column(name = "fecha_pedido",nullable= false)
    @Schema(description="fecha en que se realiza el pedido", example= "")
    private LocalDateTime fecha_pedido;

    @Column(name = "subtotal",nullable= false , precision = 10)
    @Schema(description="suma parcial de valores", example="10000")
    private Long subtotal;

    @Column(name = "metodo_pago",nullable= false , length=20)
    @Schema(description="metodo con el cual se realizara el pago", example="debito,credito,efetctivo")
    private String metodo_pago;

    @Column(name = "direccion_envio",nullable= true , length=100)
    @Schema(description="direccion donde se enviara el pedido",example="siempre viva 123")
    private String direccion_envio;
    
    @Column(name = "estado",nullable= false)
    @Schema(description="estado en que se encuentra el pedido", example="A=Activo / I=Inactivo")
    private String estado;

    @Column(name = "id_usuario",nullable= false, length=10)
    @Schema(description="identificador de usuario dueno del pedido", example="1")
    private Long id_usuario;

    
}
