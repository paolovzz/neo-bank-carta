package neo.bank.carta.application.exceptions;

public class CartaPrepagataNonTrovataException extends RuntimeException {
    
    public CartaPrepagataNonTrovataException(String idCartaPrepagata) {
        super(String.format("CartaPrepagata con id [%s] non trovata...", idCartaPrepagata));
    }
}
