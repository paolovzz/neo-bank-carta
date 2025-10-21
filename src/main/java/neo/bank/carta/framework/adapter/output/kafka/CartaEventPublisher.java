package neo.bank.carta.framework.adapter.output.kafka;

import java.util.List;
import java.util.UUID;

import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.framework.adapter.output.kafka.integration_events.converters.IntegrationEventConverterFactory;
import neo.bank.carta.application.ports.output.EventsPublisherPort;
import neo.bank.carta.domain.models.events.EventPayload;

@ApplicationScoped
@Slf4j
public class CartaEventPublisher implements EventsPublisherPort {

    @Inject
    private ObjectMapper mapper;

    @Channel("carta-notifications")
    private Emitter<String> emitter;

    @Channel("carta-error-notifications")
    private Emitter<String> errorEmitter;

    @Inject
    private IntegrationEventConverterFactory factory;

    @Override
    public void publish(String aggregateName, String aggregateId, List<EventPayload> events) {

        String key = aggregateId; 
        events.stream().forEachOrdered(ev -> {
            var converter = factory.getConverter(ev);
            Object integrationEvent = converter.convert(ev);
            Message<String> message = Message.of(toJsonString(integrationEvent))
            .addMetadata(OutgoingKafkaRecordMetadata.<String>builder()
                .withKey(key)
                .withHeaders(new RecordHeaders()
                    .add("eventType", ev.eventType().getBytes())
                    .add("aggregateName", aggregateName.getBytes())
                    .add("eventId", UUID.randomUUID().toString().getBytes()))
                .build());
            log.info("Evento inviato: {}", message);
            emitter.send(message);
        });
    }

    private String toJsonString(Object integrationEvent) {
        try {
            return mapper.writeValueAsString(integrationEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void publishError(String aggregateName, String eventName, Object body) {
        Message<String> message = Message.of(toJsonString(body))
            .addMetadata(OutgoingKafkaRecordMetadata.<String>builder()
                .withHeaders(new RecordHeaders()
                    .add("eventType", eventName.getBytes())
                    .add("aggregateName", aggregateName.getBytes())
                    .add("eventId", UUID.randomUUID().toString().getBytes()))
                .build());
            log.info("Evento inviato: {}", message);
            emitter.send(message);
    }
}