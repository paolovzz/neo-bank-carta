package neo.bank.carta.application.exceptions;

public class CartaNonTrovataException extends RuntimeException {
    
    public CartaNonTrovataException(String idCartaPrepagata) {
        super(String.format("Carta con id [%s] non trovata...", idCartaPrepagata));
    }
}
