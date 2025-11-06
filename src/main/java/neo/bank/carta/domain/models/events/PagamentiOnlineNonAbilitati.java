package neo.bank.carta.domain.models.events;

import lombok.Value;

@Value
public class PagamentiOnlineNonAbilitati implements EventPayload {

    @Override
    public String eventType() {
        return "PagamentiOnlineNonAbilitati";
    }
}
