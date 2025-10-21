package neo.bank.carta.framework.adapter.output.kafka.integration_events.converters;

import jakarta.enterprise.context.ApplicationScoped;
import neo.bank.carta.framework.adapter.output.kafka.integration_events.IEPagamentiOnlineAbilitati;
import neo.bank.carta.domain.models.events.PagamentiOnlineAbilitati;

@ApplicationScoped
public class PagamentiOnlineAbilitatiConverter implements IntegrationEventConverter<PagamentiOnlineAbilitati, IEPagamentiOnlineAbilitati>, IntegrationEventConverterMarker{

    @Override
    public IEPagamentiOnlineAbilitati convert(PagamentiOnlineAbilitati ev) {

        return new IEPagamentiOnlineAbilitati();
    }

    @Override
    public Class<PagamentiOnlineAbilitati> supportedDomainEvent() {
        return PagamentiOnlineAbilitati.class;
    }
    
}
