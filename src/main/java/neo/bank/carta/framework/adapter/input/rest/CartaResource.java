package neo.bank.carta.framework.adapter.input.rest;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import neo.bank.carta.framework.adapter.input.rest.request.CreaCartaRequest;
import neo.bank.carta.framework.adapter.input.rest.request.ImpostaAbilitazionePagamentiOnlineaRequest;
import neo.bank.carta.framework.adapter.input.rest.request.ImpostaSogliaPagamentiRequest;
import neo.bank.carta.framework.adapter.input.rest.request.ImpostaStatoCartaRequest;
import neo.bank.carta.framework.adapter.input.rest.response.CartaInfoResponse;
import neo.bank.carta.application.CartaUseCase;
import neo.bank.carta.application.ports.input.dto.CreaCartaCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaAbilitazionePagamentiOnlineCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaSogliaPagamentiGiornalieraCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaSogliaPagamentiMensileCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaStatoCartaCmd;
import neo.bank.carta.domain.models.aggregates.Carta;
import neo.bank.carta.domain.models.vo.DatiCartaView;
import neo.bank.carta.domain.models.vo.Iban;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.models.vo.UsernameCliente;

@Path("/carte")
@ApplicationScoped
public class CartaResource {

    @Inject
    private CartaUseCase app;

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response creaCarta(CreaCartaRequest request) {
        app.creaCarta(new CreaCartaCmd(new UsernameCliente(request.getUsername()), new Iban(request.getIban())));
        return Response.status(201).build();
    }

    @Path("/{numeroCarta}")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response recuperaCartaDaNumeroCarta(@PathParam(value = "numeroCarta") String numeroCarta) {

        Carta carta = app.recuperaCartaDaNumeroCarta(new NumeroCarta(numeroCarta));
        CartaInfoResponse bodyResponse = new CartaInfoResponse(carta);
        return Response.ok(bodyResponse).build();
    }

    @Path("/iban/{iban}")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response recuperaCarteDaIban(@PathParam(value = "iban") String iban) {
        List<DatiCartaView> carte = app.recuperaCarteDaIban(new Iban(iban));
        return Response.ok(carte).build();
    }

    @Path("/soglia-pagamenti-giornaliera")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaSogliaPagamentiGiornaliera( ImpostaSogliaPagamentiRequest request) {
        app.impostaSogliaPagamentiGiornaliera(new ImpostaSogliaPagamentiGiornalieraCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(request.getUsernameCliente()), request.getNuovaSoglia()));
        return Response.noContent().build();
    }

    @Path("/soglia-pagamenti-mensile")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaSogliaBonificoMensile(ImpostaSogliaPagamentiRequest request) {
        app.impostaSogliaPagamentiMensile(new ImpostaSogliaPagamentiMensileCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(request.getUsernameCliente()), request.getNuovaSoglia()));
        return Response.noContent().build();
    }
    @Path("/abilitazione-pagamenti-online")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaAbilitazionePagamentiOnline( ImpostaAbilitazionePagamentiOnlineaRequest request) {
        app.impostaAbilitazionePagamentiOnline(new ImpostaAbilitazionePagamentiOnlineCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(request.getUsernameCliente()), request.isAbilitazionePagamentiOnline()));
        return Response.noContent().build();
    }

    @Path("/stato-carta")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaStatoCarta( ImpostaStatoCartaRequest request) {
        app.impostaStatoCarta(new ImpostaStatoCartaCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(request.getUsernameCliente()), request.isStatoCarta()));
        return Response.noContent().build();
    }
}
