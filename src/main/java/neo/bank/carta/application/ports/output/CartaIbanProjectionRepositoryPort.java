package neo.bank.carta.application.ports.output;

import java.util.List;

import neo.bank.carta.domain.models.vo.DataScadenza;
import neo.bank.carta.domain.models.vo.DatiCartaView;
import neo.bank.carta.domain.models.vo.Iban;
import neo.bank.carta.domain.models.vo.IntestatarioCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;

public interface CartaIbanProjectionRepositoryPort {
    
    public void salva(NumeroCarta numeroCarta, Iban iban, IntestatarioCarta intestatarioCarta, DataScadenza dataScadenza);
    public List<DatiCartaView> recuperaDaIban (Iban iban);
}
