package cl.bakery.RyC.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import cl.bakery.RyC.Model.ryc;
import cl.bakery.RyC.Repository.rycRepository;

@Service
@Transactional
public class rycServices {

    @Autowired
    private rycRepository rycrepository;

    public List<ryc> BuscarTodoRyc() {
        return rycrepository.findAll();
    }

    public ryc BuscarUnRyc(Long idResena) {
        return rycrepository.findById(idResena)
            .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
    }

    public ryc GuardarRyc(ryc nuevaRyc) {
        return rycrepository.save(nuevaRyc);
    }

    public void EliminarRyc(Long idResena) {
        rycrepository.deleteById(idResena);
    }
}
