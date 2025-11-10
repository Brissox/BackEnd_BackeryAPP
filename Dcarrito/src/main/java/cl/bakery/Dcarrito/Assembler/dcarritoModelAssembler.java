package cl.bakery.Dcarrito.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import cl.bakery.Dcarrito.Controller.dcarritoController;
import cl.bakery.Dcarrito.Model.dcarrito;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class dcarritoModelAssembler implements RepresentationModelAssembler<dcarrito, EntityModel<dcarrito>> {

    @Override
    public EntityModel<dcarrito> toModel(dcarrito d) {
        return EntityModel.of(
            d,
            linkTo(methodOn(dcarritoController.class).BuscarDCarrito(d.getIdDetalleCarrito())).withRel("LINKS"),
            linkTo(methodOn(dcarritoController.class).ListarTodo()).withRel("todos-los-detalles")
        );
    }
}
