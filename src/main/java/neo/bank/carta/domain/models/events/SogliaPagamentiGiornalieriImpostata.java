package neo.bank.carta.domain.models.events;


public record SogliaPagamentiGiornalieriImpostata(int nuovaSogliaPagamento) implements EventPayload {

    @Override
    public String eventType() {
        return "SogliaPagamentiGiornalieriImpostata";
    }
}
