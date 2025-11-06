package neo.bank.carta.domain.models.events;

import lombok.Value;

@Value
public class SogliaPagamentiMensiliImpostata implements EventPayload {

    private int nuovaSogliaPagamento;

    @Override
    public String eventType() {
        return "SogliaPagamentiMensiliImpostata";
    }
}
