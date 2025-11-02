package neo.bank.carta.framework.adapter.output.rest;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cc")
@RegisterRestClient(configKey = "anagrafica-cc")
public interface AnagraficaContoCorrenteRestClient {

    @GET
    @Path("/{iban}")
    @Produces(MediaType.APPLICATION_JSON)
    Response verifica(@PathParam("iban") String iban, @HeaderParam("X-Authenticated-User") String username);

}
