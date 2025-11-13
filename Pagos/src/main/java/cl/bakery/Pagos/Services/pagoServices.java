package cl.bakery.Pagos.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bakery.Pagos.Model.pago;
import cl.bakery.Pagos.Repository.pagoRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional

public class pagoServices {
    @Autowired

    private pagoRepository pagosrepository;

    public List<pago> BuscarTodoPago(){
        return pagosrepository.findAll();
    }

    public pago BuscarUnPago(Long ID_PAGO){
        return pagosrepository.findById(ID_PAGO).get();

    }

    public pago GuardarPago(pago pago){
        return pagosrepository.save(pago);

    }

    public void EliminarPago(Long ID_PAGO){
        pagosrepository.deleteById(ID_PAGO);
    }

     // Buscar por usuario
    public List<pago> buscarPorPedido(Long idPedido) {
        return pagosrepository.findByIdPedido(idPedido);
    }

}