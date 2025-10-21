package neo.bank.carta.domain.models.events;


public record CartaBloccata() implements EventPayload {

    @Override
    public String eventType() {
        return "CartaBloccata";
    }
}
