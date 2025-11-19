package neo.bank.carta.framework.adapter.input.rest;

import java.time.ZoneOffset;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.application.CartaUseCase;
import neo.bank.carta.application.ports.input.dto.CreaCartaCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaAbilitazionePagamentiOnlineCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaSogliaPagamentiCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaStatoCartaCmd;
import neo.bank.carta.application.ports.input.dto.RecuperaCartaDaIbanCmd;
import neo.bank.carta.application.ports.input.dto.RecuperaCartaDaNumeroCmd;
import neo.bank.carta.domain.models.aggregates.Carta;
import neo.bank.carta.domain.models.vo.DatiCartaView;
import neo.bank.carta.domain.models.vo.Iban;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.models.vo.UsernameCliente;
import neo.bank.carta.framework.adapter.input.rest.api.CartaApi;
import neo.bank.carta.framework.adapter.input.rest.model.CartaInfoResponse;
import neo.bank.carta.framework.adapter.input.rest.model.CreaCartaRequest;
import neo.bank.carta.framework.adapter.input.rest.model.DatiCartaResponse;
import neo.bank.carta.framework.adapter.input.rest.model.ImpostaAbilitazionePagamentiOnlineRequest;
import neo.bank.carta.framework.adapter.input.rest.model.ImpostaSogliaPagamentiRequest;
import neo.bank.carta.framework.adapter.input.rest.model.ImpostaStatoCartaRequest;

@ApplicationScoped
@Slf4j
public class CartaResource implements CartaApi{

    @Inject
    private CartaUseCase app;

    @Override
    public Response creaCarta(String xAuthenticatedUser, CreaCartaRequest request) {
        app.creaCarta(new CreaCartaCmd(new UsernameCliente(xAuthenticatedUser), new Iban(request.getIban())));
        return Response.status(201).build();
    }

    @Override
    public Response impostaAbilitazionePagamentiOnline(String xAuthenticatedUser, ImpostaAbilitazionePagamentiOnlineRequest request) {
        app.impostaAbilitazionePagamentiOnline(new ImpostaAbilitazionePagamentiOnlineCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(xAuthenticatedUser), request.getAbilitazionePagamentiOnline()));
        return Response.noContent().build();
    }

    @Override
    public Response impostaSogliaPagamentiGiornaliera(String xAuthenticatedUser,  ImpostaSogliaPagamentiRequest request) {
        app.impostaSogliaPagamentiGiornaliera(new ImpostaSogliaPagamentiCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(xAuthenticatedUser), request.getNuovaSoglia()));
        return Response.noContent().build();
    }

    @Override
    public Response impostaSogliaPagamentiMensile(String xAuthenticatedUser, ImpostaSogliaPagamentiRequest request) {
        app.impostaSogliaPagamentiMensile(new ImpostaSogliaPagamentiCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(xAuthenticatedUser), request.getNuovaSoglia()));
        return Response.noContent().build();
    }

    @Override
    public Response impostaStatoCarta(String xAuthenticatedUser, ImpostaStatoCartaRequest request) {
        
        app.impostaStatoCarta(new ImpostaStatoCartaCmd(new NumeroCarta(request.getNumeroCarta()), new Iban(request.getIban()), new UsernameCliente(xAuthenticatedUser), request.getStatoCarta()));
        return Response.noContent().build();
    }

    @Override
    public Response recuperaCartaDaIban(String xAuthenticatedUser, String iban) {
        List<DatiCartaView> carte = app.recuperaCarteDaIban(new RecuperaCartaDaIbanCmd(new UsernameCliente(xAuthenticatedUser), new Iban(iban)));
        return Response.ok(carte.stream().map(c -> new DatiCartaResponse(c.getNumeroCarta(), c.getIban(), c.getIntestatario(), c.getDataScadenza()))).build();
    }

    @Override
    public Response recuperaCartaDaNumeroCarta(String xAuthenticatedUser, String numeroCarta) {
        Carta carta = app.recuperaCartaDaNumeroCarta(new RecuperaCartaDaNumeroCmd(new UsernameCliente(xAuthenticatedUser), new NumeroCarta(numeroCarta)));
        CartaInfoResponse bodyResponse = CartaInfoResponse.builder()
                                        .abilitazionePagamentiOnline(carta.getAbilitazionePagamentoOnline().name())
                                        .dataEmissione(carta.getDataEmissione().getDataOra().toInstant(ZoneOffset.UTC))
                                        .dataScadenza(carta.getDataScadenza().getDataOra().toInstant(ZoneOffset.UTC))
                                        .iban(carta.getIban().getCodice())
                                        .intestatario(carta.getIntestatarioCarta().getIntestatario())
                                        .numeroCarta(carta.getNumeroCarta().getNumero())
                                        .sogliaPagamentiGiornaliera(carta.getSogliaPagamentiGiornaliera())
                                        .sogliaPagamentiMensile(carta.getSogliaPagamentiMensile())
                                        .statoCarta(carta.getStatoCarta().name())
                                        .usernameCliente(carta.getUsernameCliente().getUsername())
                                        .build();
                    ;
        return Response.ok(bodyResponse).build();
    }

}
