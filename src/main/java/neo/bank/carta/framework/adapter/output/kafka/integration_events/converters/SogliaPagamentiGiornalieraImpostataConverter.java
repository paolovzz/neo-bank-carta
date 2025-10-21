package neo.bank.carta.framework.adapter.output.kafka.integration_events.converters;

import jakarta.enterprise.context.ApplicationScoped;
import neo.bank.carta.framework.adapter.output.kafka.integration_events.IESogliaPagamentiGiornalieraImpostata;
import neo.bank.carta.domain.models.events.SogliaPagamentiGiornalieriImpostata;

@ApplicationScoped
public class SogliaPagamentiGiornalieraImpostataConverter implements IntegrationEventConverter<SogliaPagamentiGiornalieriImpostata, IESogliaPagamentiGiornalieraImpostata>, IntegrationEventConverterMarker{

    @Override
    public IESogliaPagamentiGiornalieraImpostata convert(SogliaPagamentiGiornalieriImpostata ev) {

        return new IESogliaPagamentiGiornalieraImpostata(ev.nuovaSogliaPagamento());
    }

    @Override
    public Class<SogliaPagamentiGiornalieriImpostata> supportedDomainEvent() {
        return SogliaPagamentiGiornalieriImpostata.class;
    }
    
}
