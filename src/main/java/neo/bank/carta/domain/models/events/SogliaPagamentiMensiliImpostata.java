package neo.bank.carta.domain.models.events;

import lombok.Value;
import neo.bank.carta.domain.models.vo.SogliaPagamenti;

@Value
public class SogliaPagamentiMensiliImpostata implements EventPayload {

    private SogliaPagamenti nuovaSogliaPagamento;

    @Override
    public String eventType() {
        return "SogliaPagamentiMensiliImpostata";
    }
}
