package cl.bakery.Pedidos.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bakery.Pedidos.DTO.CrearPedidoDTO;
import cl.bakery.Pedidos.Model.Pedido;
import cl.bakery.Pedidos.Services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Pedido> crearPedido(@RequestBody CrearPedidoDTO dto) {
        return ResponseEntity.ok(pedidoService.crearPedido(dto));
    }
}
    