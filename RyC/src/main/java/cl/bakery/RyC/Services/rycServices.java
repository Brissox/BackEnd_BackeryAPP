package cl.bakery.RyC.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bakery.RyC.Repository.rycRepository;
import jakarta.transaction.Transactional;
import cl.bakery.RyC.Model.ryc;

@Service
@Transactional

public class rycServices {
    
    @Autowired

    private rycRepository rycrepository;

    public List<ryc> BuscarTodoRYC(){
        return rycrepository.findAll();
    }

    public ryc BuscarUnRYC(Long ID_RESENA){
        return rycrepository.findById(ID_RESENA).get();

    }

    public ryc GuardarRYC(ryc ryc){
        return rycrepository.save(ryc);

    }

    public void EliminarRYC(Long ID_RESENA){
        rycrepository.deleteById(ID_RESENA);
    }

     // Buscar por usuario
    public List<ryc> buscarPorUsuario(Long idUsuario) {
        return rycrepository.findByIdUsuario(idUsuario);
    }

       // Buscar por producto
    public List<ryc> buscarPorProducto(Long idProducto) {
        return rycrepository.findByIdProducto(idProducto);
    }

}
