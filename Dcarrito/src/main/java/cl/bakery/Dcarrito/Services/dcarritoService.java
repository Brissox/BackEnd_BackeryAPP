package cl.bakery.Dcarrito.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bakery.Dcarrito.Model.dcarrito;
import cl.bakery.Dcarrito.Repository.dcarritoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class dcarritoService {

    @Autowired
    private dcarritoRepository dcarritoRepository;

    public List<dcarrito> BuscarTodo() {
        return dcarritoRepository.findAll();
    }

    public dcarrito BuscarUno(Long id) {
        return dcarritoRepository.findById(id).orElse(null);
    }

    public dcarrito Guardar(dcarrito dcarrito) {
        return dcarritoRepository.save(dcarrito);
    }

    public void Eliminar(Long id) {
        dcarritoRepository.deleteById(id);
    }
}
