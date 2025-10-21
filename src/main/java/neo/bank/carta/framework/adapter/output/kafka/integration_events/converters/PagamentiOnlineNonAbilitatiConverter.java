package neo.bank.carta.framework.adapter.output.kafka.integration_events.converters;

import jakarta.enterprise.context.ApplicationScoped;
import neo.bank.carta.framework.adapter.output.kafka.integration_events.IEPagamentiOnlineNonAbilitati;
import neo.bank.carta.domain.models.events.PagamentiOnlineNonAbilitati;

@ApplicationScoped
public class PagamentiOnlineNonAbilitatiConverter implements IntegrationEventConverter<PagamentiOnlineNonAbilitati, IEPagamentiOnlineNonAbilitati>, IntegrationEventConverterMarker{

    @Override
    public IEPagamentiOnlineNonAbilitati convert(PagamentiOnlineNonAbilitati ev) {

        return new IEPagamentiOnlineNonAbilitati();
    }

    @Override
    public Class<PagamentiOnlineNonAbilitati> supportedDomainEvent() {
        return PagamentiOnlineNonAbilitati.class;
    }
    
}
