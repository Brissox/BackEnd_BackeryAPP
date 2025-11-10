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
    private pagoRepository pagorepository;

    public List<pago> BuscarTodoPagos() {
        return pagorepository.findAll();
    }

    public pago BuscarPago(Long ID_PAGO) {
        return pagorepository.findById(ID_PAGO).orElse(null);
    }

    public pago GuardarPago(pago pagoGuardar) {
        return pagorepository.save(pagoGuardar);
    }

    public void EliminarPago(Long ID_PAGO) {
        pagorepository.deleteById(ID_PAGO);
    }
}
