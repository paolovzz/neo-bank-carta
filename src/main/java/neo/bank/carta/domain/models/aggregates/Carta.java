package neo.bank.carta.domain.models.aggregates;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.domain.exceptions.BusinessRuleException;
import neo.bank.carta.domain.models.events.CartaAttivata;
import neo.bank.carta.domain.models.events.CartaBloccata;
import neo.bank.carta.domain.models.events.CartaCreata;
import neo.bank.carta.domain.models.events.EventPayload;
import neo.bank.carta.domain.models.events.PagamentiOnlineAbilitati;
import neo.bank.carta.domain.models.events.PagamentiOnlineNonAbilitati;
import neo.bank.carta.domain.models.events.SogliaPagamentiGiornalieriImpostata;
import neo.bank.carta.domain.models.events.SogliaPagamentiMensiliImpostata;
import neo.bank.carta.domain.models.vo.DataCreazione;
import neo.bank.carta.domain.models.vo.Iban;
import neo.bank.carta.domain.models.vo.IdCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.models.vo.UsernameCliente;
import neo.bank.carta.domain.services.AnagraficaContoCorrenteService;
import neo.bank.carta.domain.services.GeneratoreNumeroCartaService;


@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Carta extends AggregateRoot<Carta> implements Applier  {

    public static final String AGGREGATE_NAME = "CARTA";
    
    private IdCarta idCarta;
    private int sogliaPagamentiGiornaliera = 2500;
    private int sogliaPagamentiMensile = 3000;
    private DataCreazione dataCreazione;
    private double saldoDisponibile = 0;
    private Iban iban;
    private UsernameCliente usernameCliente;
    private NumeroCarta numeroCarta;
    private boolean cartaAttivata;
    private boolean pagamentiOnlineAbilitati;

    public static Carta crea(GeneratoreNumeroCartaService generatoreNumeroCartaService, AnagraficaContoCorrenteService anagraficaCCService, UsernameCliente usernameCliente, Iban iban) {
        
        if(!anagraficaCCService.richiediVerificaContoCorrente(usernameCliente, iban)) {
            throw new BusinessRuleException("Durante la creazione della carta si e' verificato un errore durante la verifica dell'iban e del cliente");
        }
        IdCarta idCarta =new IdCarta(UUID.randomUUID().toString());
        DataCreazione dataCreazione = new DataCreazione(LocalDateTime.now(ZoneOffset.UTC));
        double saldo = 0;
        Carta cartaPrepagata = new Carta();
        cartaPrepagata.idCarta = idCarta;
        cartaPrepagata.events(new CartaCreata(idCarta, usernameCliente, generatoreNumeroCartaService.genera(), iban, dataCreazione, saldo));
        return cartaPrepagata;
    }

    public void impostaSogliaPagamentoGiornaliero(UsernameCliente usernameCliente, int nuovaSogliaPagamento) {
        if(nuovaSogliaPagamento > sogliaPagamentiGiornaliera) {
            throw new BusinessRuleException(String.format("La Soglia pagamenti giornaliero non puo' essere maggiore della soglia pagamenti mensile"));  
        }
        if(!usernameCliente.equals(this.usernameCliente)) {
            throw new BusinessRuleException(String.format("Cliente non autorizzato ad operare sulla carta %s", usernameCliente));  
        }
        events(new SogliaPagamentiGiornalieriImpostata(nuovaSogliaPagamento));
    }

    public void impostaSogliaPagamentoMensile(UsernameCliente usernameCliente, int nuovaSogliaPagamento) {
        if(nuovaSogliaPagamento < sogliaPagamentiMensile) {
            throw new BusinessRuleException(String.format("Soglia pagamenti mensile non puo' essere maggiore della soglia pagamenti giornaliera"));  
        }
        if(!usernameCliente.equals(this.usernameCliente)) {
            throw new BusinessRuleException(String.format("Cliente non autorizzato ad operare sulla carta %s", usernameCliente));  
        }
        events(new SogliaPagamentiMensiliImpostata(nuovaSogliaPagamento));
    }

    public void impostaAbilitazionePagamentiOnline(UsernameCliente usernameCliente, boolean abilitazionePagamentiOnline) {
        if(abilitazionePagamentiOnline) {
            events(new PagamentiOnlineAbilitati());
        } else {
            events(new PagamentiOnlineNonAbilitati());
        }
    }

    public void impostaStatoCarta(UsernameCliente usernameCliente, boolean statoCarta) {
        if(statoCarta) {
            events(new CartaAttivata());
        } else {
            events(new CartaBloccata());
        }
    }

    private void apply(CartaCreata event) {
        this.dataCreazione = event.dataCreazione();
        this.iban = event.iban();
        this.idCarta = event.idCarta();
        this.saldoDisponibile = event.saldoDisponibile();

    }

    private void apply(SogliaPagamentiGiornalieriImpostata event) {
        sogliaPagamentiGiornaliera = event.nuovaSogliaPagamento();
    }

    private void apply(SogliaPagamentiMensiliImpostata event) {
        sogliaPagamentiMensile = event.nuovaSogliaPagamento();
    }

    private void apply(PagamentiOnlineAbilitati event) {
        pagamentiOnlineAbilitati = true;
    }

    private void apply(PagamentiOnlineNonAbilitati event) {
        pagamentiOnlineAbilitati = false;
    }

    private void apply(CartaAttivata event) {
        cartaAttivata = true;
    }

    private void apply(CartaBloccata event) {
        cartaAttivata = false;
    }

    @Override
    public void apply(EventPayload event) {
        switch (event) {
            case CartaCreata ev -> apply((CartaCreata) ev);
            case SogliaPagamentiGiornalieriImpostata ev -> apply((SogliaPagamentiGiornalieriImpostata) ev);
            case SogliaPagamentiMensiliImpostata ev -> apply((SogliaPagamentiMensiliImpostata) ev);
            case PagamentiOnlineAbilitati ev -> apply((PagamentiOnlineAbilitati) ev);
            case PagamentiOnlineNonAbilitati ev -> apply((PagamentiOnlineNonAbilitati) ev);
            case CartaAttivata ev -> apply((CartaAttivata) ev);
            case CartaBloccata ev -> apply((CartaBloccata) ev);
            default -> throw new IllegalArgumentException("Evento non supportato");
        }
    }
}


