package neo.bank.carta.domain.models.events;

import lombok.Value;

@Value
public class CartaBloccata implements EventPayload {

    @Override
    public String eventType() {
        return "CartaBloccata";
    }
}
