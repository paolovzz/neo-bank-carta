package neo.bank.carta.framework.adapter.output.kafka.integration_events.converters;

import neo.bank.carta.domain.models.events.EventPayload;

public interface IntegrationEventConverterMarker {
    Class<? extends EventPayload> supportedDomainEvent();
}
