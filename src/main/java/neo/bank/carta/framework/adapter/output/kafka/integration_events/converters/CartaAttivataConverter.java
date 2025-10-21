package neo.bank.carta.framework.adapter.output.kafka.integration_events.converters;

import jakarta.enterprise.context.ApplicationScoped;
import neo.bank.carta.framework.adapter.output.kafka.integration_events.IECartaAttivata;
import neo.bank.carta.domain.models.events.CartaAttivata;

@ApplicationScoped
public class CartaAttivataConverter implements IntegrationEventConverter<CartaAttivata, IECartaAttivata>, IntegrationEventConverterMarker{

    @Override
    public IECartaAttivata convert(CartaAttivata ev) {

        return new IECartaAttivata();
    }

    @Override
    public Class<CartaAttivata> supportedDomainEvent() {
        return CartaAttivata.class;
    }
    
}
