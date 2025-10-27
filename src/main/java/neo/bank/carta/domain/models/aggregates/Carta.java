package neo.bank.carta.domain.models.aggregates;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.domain.exceptions.BusinessRuleException;
import neo.bank.carta.domain.models.enums.AbilitazionePagamentoOnline;
import neo.bank.carta.domain.models.enums.StatoCarta;
import neo.bank.carta.domain.models.events.CartaAttivata;
import neo.bank.carta.domain.models.events.CartaBloccata;
import neo.bank.carta.domain.models.events.CartaCreata;
import neo.bank.carta.domain.models.events.EventPayload;
import neo.bank.carta.domain.models.events.PagamentiOnlineAbilitati;
import neo.bank.carta.domain.models.events.PagamentiOnlineNonAbilitati;
import neo.bank.carta.domain.models.events.SogliaPagamentiGiornalieriImpostata;
import neo.bank.carta.domain.models.events.SogliaPagamentiMensiliImpostata;
import neo.bank.carta.domain.models.vo.DataEmissione;
import neo.bank.carta.domain.models.vo.DataScadenza;
import neo.bank.carta.domain.models.vo.Iban;
import neo.bank.carta.domain.models.vo.IdCarta;
import neo.bank.carta.domain.models.vo.IntestatarioCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.models.vo.UsernameCliente;
import neo.bank.carta.domain.services.AnagraficaClienteService;
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
    private DataEmissione dataEmissione;
    private DataScadenza dataScadenza;
    private double saldoDisponibile = 0;
    private Iban iban;
    private UsernameCliente usernameCliente;
    private NumeroCarta numeroCarta;
    private StatoCarta statoCarta;
    private AbilitazionePagamentoOnline abilitazionePagamentoOnline = AbilitazionePagamentoOnline.DISABILITATA;
    private IntestatarioCarta intestatarioCarta;

    public static Carta crea(GeneratoreNumeroCartaService generatoreNumeroCartaService, AnagraficaContoCorrenteService anagraficaCCService, UsernameCliente usernameCliente, Iban iban, AnagraficaClienteService anagraficaClienteService) {
        
        if(!anagraficaCCService.richiediVerificaContoCorrente(usernameCliente, iban)) {
            throw new BusinessRuleException("Si e' verificato un errore durante la verifica dell'iban e del cliente");
        }
        IntestatarioCarta intestatarioCarta = anagraficaClienteService.richiediVerificaCliente(usernameCliente);
        IdCarta idCarta =new IdCarta(UUID.randomUUID().toString());
        NumeroCarta numeroCarta = generatoreNumeroCartaService.genera();
        DataEmissione dataCreazione = new DataEmissione(LocalDateTime.now(ZoneOffset.UTC));
        DataScadenza dataScadenza = new DataScadenza(LocalDateTime.now(ZoneOffset.UTC).plusYears(5));
        double saldo = 0;
        Carta carta = new Carta();
        carta.idCarta = idCarta;
        carta.numeroCarta = numeroCarta;
        carta.intestatarioCarta = intestatarioCarta;
        carta.events(new CartaCreata(idCarta, usernameCliente, numeroCarta, iban, dataCreazione, dataScadenza, saldo, intestatarioCarta));
        return carta;
    }

    public void impostaSogliaPagamentoGiornaliero(Iban iban, UsernameCliente usernameCliente, int nuovaSogliaPagamento) {
        if(nuovaSogliaPagamento > sogliaPagamentiMensile) {
            throw new BusinessRuleException(String.format("La Soglia pagamenti giornaliero non puo' essere maggiore della soglia pagamenti mensile"));  
        }
        if(!usernameCliente.equals(this.usernameCliente)) {
            throw new BusinessRuleException(String.format("Cliente [%s] non autorizzato ad operare sulla carta", usernameCliente.username()));  
        }
        if(!iban.equals(this.iban)) {
            throw new BusinessRuleException("Iban del richiedente non corrisponde a quello collegato alla carta");  
        }
        events(new SogliaPagamentiGiornalieriImpostata(nuovaSogliaPagamento));
    }

    public void impostaSogliaPagamentoMensile(Iban iban, UsernameCliente usernameCliente, int nuovaSogliaPagamento) {
        if(nuovaSogliaPagamento < sogliaPagamentiGiornaliera) {
            throw new BusinessRuleException(String.format("Soglia pagamenti mensile non puo' essere maggiore della soglia pagamenti giornaliera"));  
        }
        if(!usernameCliente.equals(this.usernameCliente)) {
            throw new BusinessRuleException(String.format("Cliente [%s] non autorizzato ad operare sulla carta", usernameCliente.username()));  
        }
        if(!iban.equals(this.iban)) {
            throw new BusinessRuleException("Iban del richiedente non corrisponde a quello collegato alla carta");  
        }
        events(new SogliaPagamentiMensiliImpostata(nuovaSogliaPagamento));
    }

    public void impostaAbilitazionePagamentiOnline(Iban iban, UsernameCliente usernameCliente, boolean abilitazionePagamentiOnline) {
        if(!usernameCliente.equals(this.usernameCliente)) {
            throw new BusinessRuleException(String.format("Cliente [%s] non autorizzato ad operare sulla carta", usernameCliente.username()));  
        }
        if(!iban.equals(this.iban)) {
            throw new BusinessRuleException("Iban del richiedente non corrisponde a quello collegato alla carta");  
        }
        if(abilitazionePagamentiOnline) {
            events(new PagamentiOnlineAbilitati());
        } else {
            events(new PagamentiOnlineNonAbilitati());
        }
    }

    public void impostaStatoCarta(Iban iban, UsernameCliente usernameCliente, boolean statoCarta) {
        if(!usernameCliente.equals(this.usernameCliente)) {
            throw new BusinessRuleException(String.format("Cliente [%s] non autorizzato ad operare sulla carta", usernameCliente.username()));  
        }
        if(!iban.equals(this.iban)) {
            throw new BusinessRuleException("Iban del richiedente non corrisponde a quello collegato alla carta");  
        }
        if(statoCarta) {
            events(new CartaAttivata());
        } else {
            events(new CartaBloccata());
        }
    }

    private void apply(CartaCreata event) {
        this.dataEmissione = event.dataEmissione();
        this.dataScadenza = event.dataScadenza();
        this.statoCarta = StatoCarta.ATTIVA;
        this.iban = event.iban();
        this.idCarta = event.idCarta();
        this.saldoDisponibile = event.saldoDisponibile();
        this.usernameCliente = event.usernameCliente();
        this.numeroCarta = event.numeroCarta();
        this.intestatarioCarta = event.intestatarioCarta();

    }

    private void apply(SogliaPagamentiGiornalieriImpostata event) {
        sogliaPagamentiGiornaliera = event.nuovaSogliaPagamento();
    }

    private void apply(SogliaPagamentiMensiliImpostata event) {
        sogliaPagamentiMensile = event.nuovaSogliaPagamento();
    }

    private void apply(PagamentiOnlineAbilitati event) {
        abilitazionePagamentoOnline = AbilitazionePagamentoOnline.ATTIVATA;
    }

    private void apply(PagamentiOnlineNonAbilitati event) {
        abilitazionePagamentoOnline = AbilitazionePagamentoOnline.DISABILITATA;
    }

    private void apply(CartaAttivata event) {
       this.statoCarta = StatoCarta.ATTIVA;
    }

    private void apply(CartaBloccata event) {
       this.statoCarta = StatoCarta.BLOCCATA;
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


