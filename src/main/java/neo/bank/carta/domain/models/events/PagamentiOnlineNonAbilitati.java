package neo.bank.carta.domain.models.events;


public record PagamentiOnlineNonAbilitati() implements EventPayload {

    @Override
    public String eventType() {
        return "PagamentiOnlineNonAbilitati";
    }
}
