package neo.bank.carta.framework.adapter.input.rest;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
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
import neo.bank.carta.application.ports.input.dto.RecuperaCartaDaIbanCmd;
import neo.bank.carta.application.ports.input.dto.RecuperaCartaDaNumeroCmd;
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

    @Context
    private HttpHeaders headers;

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response creaCarta(CreaCartaRequest request) {
        String username = recuperaUtenteAutenticato();
        app.creaCarta(new CreaCartaCmd(new UsernameCliente(username), new Iban(request.getIban())));
        return Response.status(201).build();
    }

    @Path("/{numeroCarta}")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response recuperaCartaDaNumeroCarta(@PathParam(value = "numeroCarta") String numeroCarta) {

        String username = recuperaUtenteAutenticato();
        Carta carta = app.recuperaCartaDaNumeroCarta(new RecuperaCartaDaNumeroCmd(new UsernameCliente(username), new NumeroCarta(numeroCarta)));
        CartaInfoResponse bodyResponse = new CartaInfoResponse(carta);
        return Response.ok(bodyResponse).build();
    }

    @Path("/iban/{iban}")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response recuperaCarteDaIban(@PathParam(value = "iban") String iban) {
        String username = recuperaUtenteAutenticato();
        List<DatiCartaView> carte = app.recuperaCarteDaIban(new RecuperaCartaDaIbanCmd(new UsernameCliente(username), new Iban(iban)));
        return Response.ok(carte).build();
    }

    @Path("/soglia-pagamenti-giornaliera")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaSogliaPagamentiGiornaliera( ImpostaSogliaPagamentiRequest request) {
        String username = recuperaUtenteAutenticato();
        app.impostaSogliaPagamentiGiornaliera(new ImpostaSogliaPagamentiGiornalieraCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(username), request.getNuovaSoglia()));
        return Response.noContent().build();
    }

    @Path("/soglia-pagamenti-mensile")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaSogliaBonificoMensile(ImpostaSogliaPagamentiRequest request) {

        String username = recuperaUtenteAutenticato();
        app.impostaSogliaPagamentiMensile(new ImpostaSogliaPagamentiMensileCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(username), request.getNuovaSoglia()));
        return Response.noContent().build();
    }
    @Path("/abilitazione-pagamenti-online")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaAbilitazionePagamentiOnline( ImpostaAbilitazionePagamentiOnlineaRequest request) {
        String username = recuperaUtenteAutenticato();
        app.impostaAbilitazionePagamentiOnline(new ImpostaAbilitazionePagamentiOnlineCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(username), request.isAbilitazionePagamentiOnline()));
        return Response.noContent().build();
    }

    @Path("/stato-carta")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaStatoCarta( ImpostaStatoCartaRequest request) {

        String username = recuperaUtenteAutenticato();
        app.impostaStatoCarta(new ImpostaStatoCartaCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(username), request.isStatoCarta()));
        return Response.noContent().build();
    }

    private String recuperaUtenteAutenticato() {
        String username = headers.getHeaderString("X-Authenticated-User");
        if (username == null)
            throw new NotAuthorizedException("Richiesta non autenticata");
        return username;
    }
}
