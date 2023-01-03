package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/payments")
public class SimpleDTUPayResource {

    private SimpleDTUPayService service = new SimpleDTUPayService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> getPayments() {
        return service.getPayments();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pay(Payment p) throws URISyntaxException {
        int id = -1;

        try {
            id = service.pay(p);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception: " + e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }

        return Response.created(new URI("http://localhost:8080/payments/" + id)).build();
    }

}