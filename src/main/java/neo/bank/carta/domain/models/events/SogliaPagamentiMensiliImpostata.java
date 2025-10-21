package neo.bank.carta.domain.models.events;


public record SogliaPagamentiMensiliImpostata(int nuovaSogliaPagamento) implements EventPayload {

    @Override
    public String eventType() {
        return "SogliaPagamentiMensiliImpostata";
    }
}
