package neo.bank.carta.application.exceptions;

import neo.bank.carta.domain.models.vo.IdCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;

public class CartaNonTrovataException extends RuntimeException {
    
    public CartaNonTrovataException(NumeroCarta numeroCarta) {
        super(String.format("Carta con numero [%s] non trovata...", numeroCarta.numero()));
    }
    
    public CartaNonTrovataException(IdCarta idCarta) {
        super(String.format("Carta con id [%s] non trovata...", idCarta.id()));
    }
}
