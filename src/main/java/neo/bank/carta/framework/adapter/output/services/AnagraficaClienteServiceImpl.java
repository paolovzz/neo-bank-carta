package neo.bank.carta.framework.adapter.output.services;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Chiedo verifica riguardante il cliente della carta [{}]", usernameCliente.getUsername());
        Response response = client.recuperaCliente(usernameCliente.getUsername());
        log.info("Verifica conclusa positivamente");
        ClienteInfoResponse infoRes = response.readEntity(ClienteInfoResponse.class);
        return new IntestatarioCarta(infoRes.getNome().concat(" ").concat(infoRes.getCognome()));
    }

}
