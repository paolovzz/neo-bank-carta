package neo.bank.carta.application.ports.output;

import java.util.List;

import neo.bank.carta.domain.models.aggregates.Carta;
import neo.bank.carta.domain.models.events.EventPayload;
import neo.bank.carta.domain.models.vo.IdCarta;

public interface CartaRepositoryPort {
    
    public void save(IdCarta idCarta, List<EventPayload> events);
    public Carta findById (String aggregateId);
}
