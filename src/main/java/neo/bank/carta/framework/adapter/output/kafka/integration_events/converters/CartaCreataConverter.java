package neo.bank.carta.framework.adapter.output.kafka.integration_events.converters;

import jakarta.enterprise.context.ApplicationScoped;
import neo.bank.carta.framework.adapter.output.kafka.integration_events.IECartaCreata;
import neo.bank.carta.domain.models.events.CartaCreata;

@ApplicationScoped
public class CartaCreataConverter
        implements IntegrationEventConverter<CartaCreata, IECartaCreata>, IntegrationEventConverterMarker {

    @Override
    public IECartaCreata convert(CartaCreata domainEvent) {
        return new IECartaCreata(
            domainEvent.getIdCarta().getId(),
            domainEvent.getUsernameCliente().getUsername(),
            domainEvent.getNumeroCarta().getNumero(),
            domainEvent.getIban().getCodice(),
            domainEvent.getDataEmissione().getDataOra(),
            domainEvent.getDataScadenza().getDataOra(),
            domainEvent.getSaldoDisponibile(),
            domainEvent.getIntestatarioCarta().getIntestatario()
        );
    }

    @Override
    public Class<CartaCreata> supportedDomainEvent() {
        return CartaCreata.class;
    }

}
