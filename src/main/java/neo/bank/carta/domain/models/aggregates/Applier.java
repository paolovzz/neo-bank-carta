package neo.bank.carta.domain.models.aggregates;

import neo.bank.carta.domain.models.events.EventPayload;

public interface Applier {
    void apply(EventPayload event);
}