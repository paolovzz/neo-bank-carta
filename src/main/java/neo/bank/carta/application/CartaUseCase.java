package neo.bank.carta.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.application.ports.input.dto.CreaCartaCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaAbilitazionePagamentiOnlineCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaSogliaPagamentiGiornalieraCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaSogliaPagamentiMensileCmd;
import neo.bank.carta.application.ports.input.dto.ImpostaStatoCartaCmd;
import neo.bank.carta.application.ports.output.CartaOutputPort;
import neo.bank.carta.application.ports.output.NumeroCartaProjectionRepositoryPort;
import neo.bank.carta.domain.models.aggregates.Carta;
import neo.bank.carta.domain.models.vo.IdCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;
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
    private GeneratoreNumeroCartaService generatoreNumeroCartaService;
    @Inject
    private NumeroCartaProjectionRepositoryPort numeroCartaProjectionRepositoryPort;
    
    public Carta recuperaCartaDaNumeroCarta(NumeroCarta numeroCarta) {
        log.info("Recupero info carta [{}]", numeroCarta.numero());
        IdCarta idCarta = numeroCartaProjectionRepositoryPort.recuperaDaNumeroCarta(numeroCarta);
        Carta carta = cartaOutputPort.recuperaDaId(idCarta);
        log.info("Recupero terminato");
        return carta;
    }

    public void creaCarta(CreaCartaCmd cmd) {
        log.info("Comando [creaCarta] in esecuzione...");
        Carta carta = Carta.crea(generatoreNumeroCartaService, anagraficaContoCorrenteService, cmd.getUsernameCliente(), cmd.getIban());
        cartaOutputPort.salva(carta);
        log.info("Comando [creaCarta] terminato...");
    }

    public void impostaSogliaPagamentiGiornaliera(ImpostaSogliaPagamentiGiornalieraCmd cmd) {
        log.info("Comando [impostaSogliaPagamentiGiornaliera] in esecuzione...");
        IdCarta idCarta = numeroCartaProjectionRepositoryPort.recuperaDaNumeroCarta(cmd.getNumeroCarta());
        Carta carta = cartaOutputPort.recuperaDaId(idCarta);
        carta.impostaSogliaPagamentoGiornaliero(cmd.getUsernameCliente(), cmd.getNuovaSogliaPagamenti() );
        cartaOutputPort.salva(carta);
        log.info("Comando [impostaSogliaPagamentiGiornaliera] terminato...");
    }

    public void impostaSogliaPagamentiMensile(ImpostaSogliaPagamentiMensileCmd cmd) {
        log.info("Comando [impostaSogliaPagamentiMensile] in esecuzione...");
        IdCarta idCarta = numeroCartaProjectionRepositoryPort.recuperaDaNumeroCarta(cmd.getNumeroCarta());
        Carta carta = cartaOutputPort.recuperaDaId(idCarta);
        carta.impostaSogliaPagamentoMensile(cmd.getUsernameCliente(), cmd.getNuovaSogliaPagamenti());
        cartaOutputPort.salva(carta);
        log.info("Comando [impostaSogliaPagamentiMensile] terminato...");
    }

    public void impostaStatoCarta(ImpostaStatoCartaCmd cmd) {
        log.info("Comando [impostaStatoCarta] in esecuzione...");
        IdCarta idCarta = numeroCartaProjectionRepositoryPort.recuperaDaNumeroCarta(cmd.getNumeroCarta());
        Carta carta = cartaOutputPort.recuperaDaId(idCarta);
        carta.impostaStatoCarta(cmd.getUsernameCliente(), cmd.isStatoCarta() );
        cartaOutputPort.salva(carta);
        log.info("Comando [impostaStatoCarta] terminato...");
    }

    public void impostaAbilitazionePagamentiOnline(ImpostaAbilitazionePagamentiOnlineCmd cmd) {
        log.info("Comando [impostaAbilitazionePagamentiOnline] in esecuzione...");
        IdCarta idCarta = numeroCartaProjectionRepositoryPort.recuperaDaNumeroCarta(cmd.getNumeroCarta());
        Carta carta = cartaOutputPort.recuperaDaId(idCarta);
        carta.impostaAbilitazionePagamentiOnline(cmd.getUsernameCliente(), cmd.isAbilitazione() );
        cartaOutputPort.salva(carta);
        log.info("Comando [impostaAbilitazionePagamentiOnline] terminato...");
    }

}