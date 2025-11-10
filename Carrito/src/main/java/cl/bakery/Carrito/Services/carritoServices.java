package cl.bakery.Carrito.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bakery.Carrito.Model.carrito;
import cl.bakery.Carrito.Repository.carritoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class carritoServices {

    @Autowired
    private carritoRepository carritorepository;

    // Buscar todos los carritos
    public List<carrito> BuscarTodoCarritos() {
        return carritorepository.findAll();
    }

    // Buscar un carrito por ID
    public carrito BuscarUnCarrito(Long ID_CARRITO) {
        return carritorepository.findById(ID_CARRITO).orElse(null);
    }

    // Guardar un carrito
    public carrito GuardarCarrito(carrito carrito) {
        return carritorepository.save(carrito);
    }

    // Eliminar un carrito
    public void EliminarCarrito(Long ID_CARRITO) {
        carritorepository.deleteById(ID_CARRITO);
    }
}
