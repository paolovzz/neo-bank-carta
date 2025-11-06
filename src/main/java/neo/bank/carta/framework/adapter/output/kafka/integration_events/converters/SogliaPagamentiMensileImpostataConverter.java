package neo.bank.carta.framework.adapter.output.kafka.integration_events.converters;

import jakarta.enterprise.context.ApplicationScoped;
import neo.bank.carta.framework.adapter.output.kafka.integration_events.IESogliaPagamentiMensileImpostata;
import neo.bank.carta.domain.models.events.SogliaPagamentiMensiliImpostata;

@ApplicationScoped
public class SogliaPagamentiMensileImpostataConverter implements IntegrationEventConverter<SogliaPagamentiMensiliImpostata, IESogliaPagamentiMensileImpostata>, IntegrationEventConverterMarker{

    @Override
    public IESogliaPagamentiMensileImpostata convert(SogliaPagamentiMensiliImpostata ev) {

        return new IESogliaPagamentiMensileImpostata(ev.getNuovaSogliaPagamento());
    }

    @Override
    public Class<SogliaPagamentiMensiliImpostata> supportedDomainEvent() {
        return SogliaPagamentiMensiliImpostata.class;
    }
    
}
