package cl.bakery.Descuentos.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.bakery.Descuentos.Model.descuento;
import cl.bakery.Descuentos.Repository.DescuentoRepository;

@Service
public class DescuentoService {

    private final DescuentoRepository repository;

    public DescuentoService(DescuentoRepository repository) {
        this.repository = repository;
    }

    public List<descuento> listar() {
        return repository.findAll();
    }

    public descuento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado"));
    }

    public descuento crear(descuento d) {

        if (repository.existsByCodigo(d.getCodigo())) {
            throw new RuntimeException("El código ya existe");
        }

        return repository.save(d);
    }

    public descuento actualizar(Integer id, descuento datos) {
        descuento d = buscarPorId(id);

        d.setCodigo(datos.getCodigo());
        d.setDescripcion(datos.getDescripcion());
        d.setValor(datos.getValor());
        d.setTipo(datos.getTipo());

        return repository.save(d);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
