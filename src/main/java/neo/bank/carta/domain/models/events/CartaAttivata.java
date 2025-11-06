package neo.bank.carta.domain.models.events;

import lombok.Value;

@Value
public class CartaAttivata implements EventPayload {

    @Override
    public String eventType() {
        return "CartaAttivata";
    }
}
