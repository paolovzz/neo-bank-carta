package neo.bank.carta.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.application.exceptions.CartaNonTrovataException;
import neo.bank.carta.application.ports.input.dto.CreaCartaCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaAbilitazionePagamentiOnlineCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaSogliaPagamentiCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaStatoCartaCmd;
import neo.bank.carta.application.ports.input.dto.RecuperaCartaDaIbanCmd;
import neo.bank.carta.application.ports.input.dto.RecuperaCartaDaNumeroCmd;
import neo.bank.carta.application.ports.output.CartaIbanProjectionRepositoryPort;
import neo.bank.carta.application.ports.output.CartaOutputPort;
import neo.bank.carta.application.ports.output.NumeroCartaProjectionRepositoryPort;
import neo.bank.carta.domain.models.aggregates.Carta;
import neo.bank.carta.domain.models.vo.DatiCartaView;
import neo.bank.carta.domain.models.vo.IdCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.services.AnagraficaClienteService;
import neo.bank.carta.domain.services.AnagraficaContoCorrenteService;
import neo.bank.carta.domain.services.GeneratoreNumeroCartaService;

@ApplicationScoped
@Slf4j
public class CartaUseCase {
    
    @Inject
    private CartaOutputPort cartaOutputPort;
    @Inject
    private AnagraficaContoCorrenteService anagraficaContoCorrenteService;
    @Inject
    private AnagraficaClienteService anagraficaClienteService;
    @Inject
    private GeneratoreNumeroCartaService generatoreNumeroCartaService;
    @Inject
    private NumeroCartaProjectionRepositoryPort numeroCartaProjectionRepositoryPort;

    @Inject
    private CartaIbanProjectionRepositoryPort cartaIbanProjectionRepositoryPort;
    
    public Carta recuperaCartaDaNumeroCarta(RecuperaCartaDaNumeroCmd cmd) {
        log.info("Recupero info carta [{}]", cmd.getNumeroCarta().getNumero());
        IdCarta idCarta = recuperaIdCarta(cmd.getNumeroCarta());
        Carta carta = cartaOutputPort.recuperaDaId(idCarta);
        carta.verificaAccessoCliente(cmd.getUsernameCliente());
        log.info("Recupero terminato");
        return carta;
    }
    
    public List<DatiCartaView> recuperaCarteDaIban(RecuperaCartaDaIbanCmd cmd) {
        log.info("Recupero carte da iban [{}]", cmd.getIban().getCodice());
        List<DatiCartaView> datiCarte = cartaIbanProjectionRepositoryPort.recuperaDaIbanEIntestatario(cmd.getIban(), cmd.getUsernameCliente());
        log.info("Recupero terminato");
        return datiCarte;
    }

    public void creaCarta(CreaCartaCmd cmd) {
        log.info("Comando [creaCarta] in esecuzione...");
        Carta carta = Carta.crea(generatoreNumeroCartaService, anagraficaContoCorrenteService, cmd.getUsernameCliente(), cmd.getIban(), anagraficaClienteService);
        cartaOutputPort.salva(carta);
        numeroCartaProjectionRepositoryPort.salva(carta.getNumeroCarta(), carta.getIdCarta());
        cartaIbanProjectionRepositoryPort.salva(carta.getNumeroCarta(), cmd.getIban(), carta.getIntestatarioCarta(), carta.getDataScadenza(), cmd.getUsernameCliente());
        log.info("Comando [creaCarta] terminato...");
    }

    public void impostaSogliaPagamentiGiornaliera(ImpostaSogliaPagamentiCmd cmd) {
        log.info("Comando [impostaSogliaPagamentiGiornaliera] in esecuzione...");
        IdCarta idCarta = recuperaIdCarta(cmd.getNumeroCarta());

        Carta carta = cartaOutputPort.recuperaDaId(idCarta);
        carta.impostaSogliaPagamentoGiornaliero(cmd.getIban(), cmd.getUsernameCliente(), cmd.getNuovaSogliaPagamenti() );
        cartaOutputPort.salva(carta);
        log.info("Comando [impostaSogliaPagamentiGiornaliera] terminato...");
    }

    public void impostaSogliaPagamentiMensile(ImpostaSogliaPagamentiCmd cmd) {
        log.info("Comando [impostaSogliaPagamentiMensile] in esecuzione...");
        IdCarta idCarta = recuperaIdCarta(cmd.getNumeroCarta());
        Carta carta = cartaOutputPort.recuperaDaId(idCarta);
        carta.impostaSogliaPagamentoMensile(cmd.getIban(), cmd.getUsernameCliente(), cmd.getNuovaSogliaPagamenti());
        cartaOutputPort.salva(carta);
        log.info("Comando [impostaSogliaPagamentiMensile] terminato...");
    }

    public void impostaStatoCarta(ImpostaStatoCartaCmd cmd) {
        log.info("Comando [impostaStatoCarta] in esecuzione...");
        IdCarta idCarta = recuperaIdCarta(cmd.getNumeroCarta());
        Carta carta = cartaOutputPort.recuperaDaId(idCarta);
        carta.impostaStatoCarta(cmd.getIban(), cmd.getUsernameCliente(), cmd.isStatoCarta() );
        cartaOutputPort.salva(carta);
        log.info("Comando [impostaStatoCarta] terminato...");
    }

    public void impostaAbilitazionePagamentiOnline(ImpostaAbilitazionePagamentiOnlineCmd cmd) {
        log.info("Comando [impostaAbilitazionePagamentiOnline] in esecuzione...");
        IdCarta idCarta = recuperaIdCarta(cmd.getNumeroCarta());
        Carta carta = cartaOutputPort.recuperaDaId(idCarta);
        carta.impostaAbilitazionePagamentiOnline(cmd.getIban(), cmd.getUsernameCliente(), cmd.isAbilitazione() );
        cartaOutputPort.salva(carta);
        log.info("Comando [impostaAbilitazionePagamentiOnline] terminato...");
    }

    private IdCarta recuperaIdCarta(NumeroCarta numeroCarta) {
        IdCarta idCarta = numeroCartaProjectionRepositoryPort.recuperaDaNumeroCarta(numeroCarta);
        if(idCarta == null) {
            throw new CartaNonTrovataException(numeroCarta);
        }
        return idCarta;
    }

}