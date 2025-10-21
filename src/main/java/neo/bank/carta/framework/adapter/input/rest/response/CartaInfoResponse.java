package neo.bank.carta.framework.adapter.input.rest.response;

import java.time.LocalDateTime;

import lombok.Getter;
import neo.bank.carta.domain.models.aggregates.Carta;

@Getter
public class CartaInfoResponse {
   
    private String idCartaPrepagata;
    private int sogliaPagamentiGiornaliera;
    private int sogliaPagamentiMensile;
    private LocalDateTime dataEmissione;
    private LocalDateTime dataScadenza;
    private String statoCarta;
    private double saldoDisponibile;
    private String iban;
    private String usernameCliente;
    private String numeroCarta;

    public CartaInfoResponse(Carta carta) {
        this.idCartaPrepagata = carta.getIdCarta().id();
        this.sogliaPagamentiGiornaliera = carta.getSogliaPagamentiGiornaliera();
        this.sogliaPagamentiMensile = carta.getSogliaPagamentiMensile();
        this.dataEmissione = carta.getDataEmissione().dataOra();
        this.dataScadenza = carta.getDataScadenza().dataOra();
        this.statoCarta = carta.getStatoCarta().name();
        this.iban = carta.getIban().codice();
        this.usernameCliente = carta.getUsernameCliente().username();
        this.numeroCarta = carta.getNumeroCarta().numero();
        this.saldoDisponibile = carta.getSaldoDisponibile();

    }

    

}
