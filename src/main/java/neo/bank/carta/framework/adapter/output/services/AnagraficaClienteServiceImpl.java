package neo.bank.carta.framework.adapter.output.services;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.domain.exceptions.BusinessRuleException;
import neo.bank.carta.domain.models.vo.IntestatarioCarta;
import neo.bank.carta.domain.models.vo.UsernameCliente;
import neo.bank.carta.domain.services.AnagraficaClienteService;
import neo.bank.carta.framework.adapter.output.rest.AnagraficaClienteRestClient;
import neo.bank.carta.framework.adapter.output.rest.response.ClienteInfoResponse;

@ApplicationScoped
@Slf4j
public class AnagraficaClienteServiceImpl implements AnagraficaClienteService {

    @RestClient
    private final AnagraficaClienteRestClient client;

    @Inject
    public AnagraficaClienteServiceImpl(@RestClient AnagraficaClienteRestClient client) {
        this.client = client;
    }

    @Override
    public IntestatarioCarta recuperaDatiIntestatario(UsernameCliente usernameCliente) {
         try {
        log.info("Richiedo i dati del cliente [{}]", usernameCliente.getUsername());
        Response response = client.recuperaCliente(usernameCliente.getUsername());
        ClienteInfoResponse infoRes = response.readEntity(ClienteInfoResponse.class);
        log.info("Recupero dati intestatario terminato");
        return new IntestatarioCarta(infoRes.getNome().concat(" ").concat(infoRes.getCognome()));
        } catch(WebApplicationException ex) {
            log.error("Errore durante il recupero dei dati del cliente", ex);
            throw new BusinessRuleException(String.format("Errore durante il recupero dati del cliente. %s", ex.getMessage()));
        }
    }

}
