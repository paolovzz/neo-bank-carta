package neo.bank.carta.framework.adapter.output.kafka.integration_events.converters;

import neo.bank.carta.domain.models.events.EventPayload;

public interface IntegrationEventConverter<DE extends EventPayload, IE> {
    IE convert(DE domainEvent);
}