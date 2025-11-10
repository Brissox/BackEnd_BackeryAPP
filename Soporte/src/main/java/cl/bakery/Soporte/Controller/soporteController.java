package cl.bakery.Soporte.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import cl.bakery.Soporte.Model.soporte;
import cl.bakery.Soporte.Services.soporteServices;
import cl.bakery.Soporte.Assembler.soporteModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/Soporte")
public class soporteController {

    @Autowired
    private soporteServices soporteServices;

    @Autowired
    private soporteModelAssembler soporteAssembler;

    @GetMapping
    public CollectionModel<EntityModel<soporte>> ListarTodo() {
        List<EntityModel<soporte>> soportes = soporteServices.BuscarTodoSoportes()
                .stream()
                .map(soporteAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(soportes,
                linkTo(methodOn(soporteController.class).ListarTodo()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<soporte> BuscarSoporte(@PathVariable Long id) {
        soporte soporte = soporteServices.BuscarUnSoporte(id);
        return soporteAssembler.toModel(soporte);
    }

    @PostMapping
    public soporte GuardarSoporte(@RequestBody soporte soporte) {
        return soporteServices.GuardarSoporte(soporte);
    }

    @DeleteMapping("/{id}")
    public void EliminarSoporte(@PathVariable Long id) {
        soporteServices.EliminarSoporte(id);
    }
}
