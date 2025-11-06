package neo.bank.carta.framework.adapter.input.rest.response;

import java.time.LocalDateTime;

import lombok.Getter;
import neo.bank.carta.domain.models.aggregates.Carta;

@Getter
public class CartaInfoResponse {
   
    private String idCarta;
    private int sogliaPagamentiGiornaliera;
    private int sogliaPagamentiMensile;
    private LocalDateTime dataEmissione;
    private LocalDateTime dataScadenza;
    private String statoCarta;
    private double saldoDisponibile;
    private String iban;
    private String usernameCliente;
    private String numeroCarta;
    private String abilitazionePagamentiOnline;
    private String intestatario;


    public CartaInfoResponse(Carta carta) {
        this.idCarta = carta.getIdCarta().getId();
        this.sogliaPagamentiGiornaliera = carta.getSogliaPagamentiGiornaliera();
        this.sogliaPagamentiMensile = carta.getSogliaPagamentiMensile();
        this.dataEmissione = carta.getDataEmissione().getDataOra();
        this.dataScadenza = carta.getDataScadenza().getDataOra();
        this.statoCarta = carta.getStatoCarta().name();
        this.iban = carta.getIban().getCodice();
        this.usernameCliente = carta.getUsernameCliente().getUsername();
        this.numeroCarta = carta.getNumeroCarta().getNumero();
        this.saldoDisponibile = carta.getSaldoDisponibile();
        this.abilitazionePagamentiOnline = carta.getAbilitazionePagamentoOnline().name();
        this.intestatario = carta.getIntestatarioCarta().getIntestatario();
    }

    

}
