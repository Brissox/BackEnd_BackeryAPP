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

    private soporteRepository soporterepository;

    public List<soporte> BuscarTodoSoporte(){
        return soporterepository.findAll();
    }

    public soporte BuscarUnSoporte(Long ID_SOPORTE){
        return soporterepository.findById(ID_SOPORTE).get();

    }

    public soporte GuardarSoporte(soporte soporte){
        return soporterepository.save(soporte);

    }

    public void EliminarSoporte(Long ID_SOPORTE){
        soporterepository.deleteById(ID_SOPORTE);
    }

     // Buscar por usuario
    public List<soporte> buscarPorUsuario(Long idUsuario) {
        return soporterepository.findByIdUsuario(idUsuario);
    }

}
