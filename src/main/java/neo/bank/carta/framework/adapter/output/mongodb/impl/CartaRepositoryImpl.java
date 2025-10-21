package neo.bank.carta.framework.adapter.output.mongodb.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.framework.adapter.output.mongodb.entities.EventStoreEntity;
import neo.bank.carta.application.ports.output.CartaRepositoryPort;
import neo.bank.carta.domain.models.aggregates.Carta;
import neo.bank.carta.domain.models.events.EventPayload;
import neo.bank.carta.domain.models.vo.IdCarta;

@ApplicationScoped
@Slf4j
public class CartaRepositoryImpl implements PanacheMongoRepository<EventStoreEntity>, CartaRepositoryPort {

    @Inject
    private ObjectMapper mapper;

    @Override
    public void save(IdCarta idCarta, List<EventPayload> events) {
        long nextSequence = getNextSequence(idCarta.id());
        try {
            for (EventPayload ev : events) {
                EventStoreEntity entity;

                entity = new EventStoreEntity(idCarta.id(), ev.eventType(), mapper.writeValueAsString(ev),
                        nextSequence);

                entity.persist();
                nextSequence += 1;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Carta findById(String aggregateId) {
        
        List<EventPayload> eventPayloads = EventStoreEntity.find("aggregateId = ?1", aggregateId)
                .stream()
                .sorted(Comparator.comparingLong(e -> ((EventStoreEntity) e).getSequence()))
                .map(e -> deserializeEvent((EventStoreEntity) e))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        
        if(eventPayloads == null || eventPayloads.isEmpty()) {
            return null;
        }
        Carta cc = new Carta();
        for (EventPayload ev : eventPayloads) {
            cc.apply(ev);
        }
        return cc;
    }

    private EventPayload deserializeEvent(EventStoreEntity e) {
        try {
            Class<?> clazz = Class.forName(EventPayload.class.getPackageName().concat(".").concat( e.getEventType()));
            return (EventPayload) mapper.readValue(e.getPayload(), clazz);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private long getNextSequence(String aggregateId) {
        EventStoreEntity last = EventStoreEntity.find("aggregateId = ?1", aggregateId)
                .stream()
                .map(e -> (EventStoreEntity) e)
                .max(Comparator.comparingLong(se -> se.getSequence()))
                .orElse(null);
        return last != null ? last.getSequence() + 1 : 1;
    }
}
