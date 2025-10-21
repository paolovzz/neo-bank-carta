package neo.bank.carta.application.ports.output;

import neo.bank.carta.domain.models.vo.IdCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;

public interface NumeroCartaProjectionRepositoryPort {
    
    public void salva(NumeroCarta numeroCarta, IdCarta idCarta);
    public IdCarta recuperaDaNumeroCarta (NumeroCarta numeroCarta);
}
