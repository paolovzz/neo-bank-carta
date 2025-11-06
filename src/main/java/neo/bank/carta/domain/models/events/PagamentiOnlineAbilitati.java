package neo.bank.carta.domain.models.events;

import lombok.Value;

@Value
public class PagamentiOnlineAbilitati implements EventPayload {

    @Override
    public String eventType() {
        return "PagamentiOnlineAbilitati";
    }
}
