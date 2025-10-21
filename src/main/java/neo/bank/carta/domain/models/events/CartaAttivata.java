package neo.bank.carta.domain.models.events;


public record CartaAttivata() implements EventPayload {

    @Override
    public String eventType() {
        return "CartaAttivata";
    }
}
