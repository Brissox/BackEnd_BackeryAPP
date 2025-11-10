package cl.bakery.Pagos.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import cl.bakery.Pagos.Controller.pagoController;
import cl.bakery.Pagos.Model.pago;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class pagoModelAssembler implements RepresentationModelAssembler<pago, EntityModel<pago>> {

    @Override
    public EntityModel<pago> toModel(pago p) {
        return EntityModel.of(
            p,
            linkTo(methodOn(pagoController.class).BuscarPago(p.getIdPago())).withRel("LINKS"),
            linkTo(methodOn(pagoController.class).ListarTodo()).withRel("todos-los-pagos")
        );
    }
}
