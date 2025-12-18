package neo.bank.carta.domain.models.aggregates;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.domain.exceptions.AccessoNonAutorizzatoException;
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
import neo.bank.carta.domain.models.vo.SogliaPagamenti;
import neo.bank.carta.domain.models.vo.UsernameCliente;
import neo.bank.carta.domain.services.AnagraficaClienteService;
import neo.bank.carta.domain.services.AnagraficaContoCorrenteService;
import neo.bank.carta.domain.services.GeneratoreNumeroCartaService;


@Slf4j
@Getter
@NoArgsConstructor
public class Carta extends AggregateRoot<Carta> implements Applier  {

    public static final String AGGREGATE_NAME = "CARTA";
    
    private IdCarta idCarta;
    private SogliaPagamenti sogliaPagamentiGiornaliera = new SogliaPagamenti(2500);
    private SogliaPagamenti sogliaPagamentiMensile = new SogliaPagamenti(3000);
    private DataEmissione dataEmissione;
    private DataScadenza dataScadenza;
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
        IntestatarioCarta intestatarioCarta = anagraficaClienteService.recuperaDatiIntestatario(usernameCliente);
        IdCarta idCarta =new IdCarta(UUID.randomUUID().toString());
        NumeroCarta numeroCarta = generatoreNumeroCartaService.genera();
        DataEmissione dataCreazione = new DataEmissione(LocalDateTime.now(ZoneOffset.UTC));
        DataScadenza dataScadenza = new DataScadenza(LocalDateTime.now(ZoneOffset.UTC).plusYears(5));
        Carta carta = new Carta();
        carta.idCarta = idCarta;
        carta.numeroCarta = numeroCarta;
        carta.intestatarioCarta = intestatarioCarta;
        carta.events(new CartaCreata(idCarta, usernameCliente, numeroCarta, iban, dataCreazione, dataScadenza, intestatarioCarta));
        return carta;
    }

    public void impostaSogliaPagamentiGiornaliera(UsernameCliente usernameCliente, SogliaPagamenti nuovaSogliaPagamento) {
        verificaIntestatario(usernameCliente);
        if(nuovaSogliaPagamento.getSoglia() > sogliaPagamentiMensile.getSoglia()) {
            throw new BusinessRuleException(String.format("La Soglia pagamenti giornaliero non puo' essere maggiore della soglia pagamenti mensile"));  
        }
        events(new SogliaPagamentiGiornalieriImpostata(nuovaSogliaPagamento));
    }

    public void impostaSogliaPagamentiMensile(UsernameCliente usernameCliente, SogliaPagamenti nuovaSogliaPagamento) {
        verificaIntestatario(usernameCliente);
        if(nuovaSogliaPagamento.getSoglia() < sogliaPagamentiGiornaliera.getSoglia()) {
            throw new BusinessRuleException(String.format("Soglia pagamenti mensile non puo' essere maggiore della soglia pagamenti giornaliera"));  
        }
        events(new SogliaPagamentiMensiliImpostata(nuovaSogliaPagamento));
    }

    public void impostaAbilitazionePagamentiOnline(UsernameCliente usernameCliente, boolean abilitazionePagamentiOnline) {
        verificaIntestatario(usernameCliente);
        if(abilitazionePagamentiOnline) {
            events(new PagamentiOnlineAbilitati());
        } else {
            events(new PagamentiOnlineNonAbilitati());
        }
    }

    public void impostaStatoCarta(UsernameCliente usernameCliente, boolean statoCarta) {
        verificaIntestatario(usernameCliente);
        if(!iban.equals(this.iban)) {
            throw new BusinessRuleException("Iban del richiedente non corrisponde a quello collegato alla carta");  
        }
        if(statoCarta) {
            events(new CartaAttivata());
        } else {
            events(new CartaBloccata());
        }
    }

     public void verificaIntestatario(UsernameCliente usernameCliente) {
        if( !this.usernameCliente.equals(usernameCliente)){
            throw new AccessoNonAutorizzatoException(usernameCliente.getUsername());
        }
    }

    private void apply(CartaCreata event) {
        this.dataEmissione = event.getDataEmissione();
        this.dataScadenza = event.getDataScadenza();
        this.statoCarta = StatoCarta.ATTIVA;
        this.iban = event.getIban();
        this.idCarta = event.getIdCarta();
        this.usernameCliente = event.getUsernameCliente();
        this.numeroCarta = event.getNumeroCarta();
        this.intestatarioCarta = event.getIntestatarioCarta();

    }

    private void apply(SogliaPagamentiGiornalieriImpostata event) {
        sogliaPagamentiGiornaliera = event.getNuovaSogliaPagamento();
    }

    private void apply(SogliaPagamentiMensiliImpostata event) {
        sogliaPagamentiMensile = event.getNuovaSogliaPagamento();
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


