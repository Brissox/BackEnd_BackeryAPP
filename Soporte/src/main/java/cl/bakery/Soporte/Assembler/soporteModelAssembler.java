package cl.bakery.Soporte.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import cl.bakery.Soporte.Controller.soporteController;
import cl.bakery.Soporte.Model.soporte;

@Component

public class soporteModelAssembler implements RepresentationModelAssembler<soporte, EntityModel<soporte>>{   
     @Override
    public EntityModel<soporte> toModel(soporte p){
        return EntityModel.of(
            p,
            linkTo(methodOn(soporteController.class).BuscarUnSoporte(p.getId_soporte())).withRel("LINKS"),
            linkTo(methodOn(soporteController.class).ListarSoportes()).withRel("todas-los-productos")
           /* linkTo(methodOn(rycController.class).ActualizarProducto(p.getId_producto(), p)).withRel("actualiza-una-venta")*/ 
        );
    }
}
    