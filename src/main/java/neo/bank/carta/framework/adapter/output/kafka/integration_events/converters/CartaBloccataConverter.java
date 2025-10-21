package neo.bank.carta.framework.adapter.output.kafka.integration_events.converters;

import jakarta.enterprise.context.ApplicationScoped;
import neo.bank.carta.framework.adapter.output.kafka.integration_events.IECartaBloccata;
import neo.bank.carta.domain.models.events.CartaBloccata;

@ApplicationScoped
public class CartaBloccataConverter implements IntegrationEventConverter<CartaBloccata, IECartaBloccata>, IntegrationEventConverterMarker{

    @Override
    public IECartaBloccata convert(CartaBloccata ev) {

        return new IECartaBloccata();
    }

    @Override
    public Class<CartaBloccata> supportedDomainEvent() {
        return CartaBloccata.class;
    }
    
}
