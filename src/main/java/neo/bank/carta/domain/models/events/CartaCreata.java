package neo.bank.carta.domain.models.events;

import neo.bank.carta.domain.models.vo.DataEmissione;
import neo.bank.carta.domain.models.vo.DataScadenza;
import neo.bank.carta.domain.models.vo.Iban;
import neo.bank.carta.domain.models.vo.IdCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.models.vo.UsernameCliente;

public record CartaCreata(
        IdCarta idCarta,
        UsernameCliente usernameCliente,
        NumeroCarta numeroCarta,
        Iban iban,
        DataEmissione dataEmissione,
        DataScadenza dataScadenza,
        double saldoDisponibile) implements EventPayload {

    @Override
    public String eventType() {
        return "CartaCreata";
    }
}
