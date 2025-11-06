package neo.bank.carta.domain.models.events;

import lombok.Value;
import neo.bank.carta.domain.models.vo.DataEmissione;
import neo.bank.carta.domain.models.vo.DataScadenza;
import neo.bank.carta.domain.models.vo.Iban;
import neo.bank.carta.domain.models.vo.IdCarta;
import neo.bank.carta.domain.models.vo.IntestatarioCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.models.vo.UsernameCliente;

@Value
public class CartaCreata implements EventPayload {

    private IdCarta idCarta;
    private UsernameCliente usernameCliente;
    private NumeroCarta numeroCarta;
    private Iban iban;
    private DataEmissione dataEmissione;
    private DataScadenza dataScadenza;
    private double saldoDisponibile;
    private IntestatarioCarta intestatarioCarta;

    @Override
    public String eventType() {
        return "CartaCreata";
    }
}
