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
    private carritoRepository carritoRepository;

    // Obtener todos los carritos
    public List<carrito> buscarTodos() {
        return carritoRepository.findAll();
    }

    // Buscar un carrito específico
    public carrito buscarPorId(Long ID_CARRITO) {
        return carritoRepository.findById(ID_CARRITO).get();
    }

    // Buscar por usuario
    public List<carrito> buscarPorUsuario(Long idUsuario) {
        return carritoRepository.findByIdUsuario(idUsuario);
    }

    // Guardar o actualizar un carrito
    public carrito guardarCarrito(carrito carrito) {
        return carritoRepository.save(carrito);
    }

    // Eliminar un carrito
    public void eliminarCar(Long ID_CARRITO) {
        carritoRepository.deleteById(ID_CARRITO);
    }
}