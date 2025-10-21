package neo.bank.carta.framework.adapter.output.services;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.domain.services.AnagraficaContoCorrenteService;
import neo.bank.carta.domain.models.vo.*;
import neo.bank.carta.framework.adapter.output.rest.AnagraficaContoCorrenteRestClient;
@ApplicationScoped
@Slf4j
public class AnagraficaContoCorrenteServiceImpl implements AnagraficaContoCorrenteService{
    
    @RestClient
    private final AnagraficaContoCorrenteRestClient client;

    @Inject
    public AnagraficaContoCorrenteServiceImpl(@RestClient AnagraficaContoCorrenteRestClient client) {
        this.client = client;
    }

    @Override
    public boolean richiediVerificaContoCorrente(UsernameCliente usernameCliente,  Iban iban) {
       log.info("Chiedo verifica riguardante l'iban e il cliente della carta [{}]", usernameCliente.username());
        try {
            client.verifica(iban.codice(), usernameCliente.username());
            log.info("Verifica conclusa positivamente");
            return true;
        } catch(WebApplicationException ex) {
            if(ex.getResponse().getStatus() == 404) {
                return false;
            }
            throw ex;
        }
    }
}
