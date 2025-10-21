package neo.bank.carta.domain.models.events;


public record PagamentiOnlineAbilitati() implements EventPayload {

    @Override
    public String eventType() {
        return "PagamentiOnlineAbilitati";
    }
}
