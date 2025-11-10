package cl.bakery.Soporte.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bakery.Soporte.Model.soporte;
import cl.bakery.Soporte.Repository.soporteRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class soporteServices {

    @Autowired
    private soporteRepository soporteRepository;

    public List<soporte> BuscarTodoSoportes() {
        return soporteRepository.findAll();
    }

    public soporte BuscarUnSoporte(Long idSoporte) {
        return soporteRepository.findById(idSoporte).orElse(null);
    }

    public soporte GuardarSoporte(soporte soporte) {
        return soporteRepository.save(soporte);
    }

    public void EliminarSoporte(Long idSoporte) {
        soporteRepository.deleteById(idSoporte);
    }
}
