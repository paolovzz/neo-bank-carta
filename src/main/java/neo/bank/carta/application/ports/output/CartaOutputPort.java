package neo.bank.carta.application.ports.output;

import neo.bank.carta.domain.models.aggregates.Carta;
import neo.bank.carta.domain.models.vo.IdCarta;

public interface CartaOutputPort {
    
    public void salva(Carta carta);
    public Carta recuperaDaId(IdCarta idCarta);
}
