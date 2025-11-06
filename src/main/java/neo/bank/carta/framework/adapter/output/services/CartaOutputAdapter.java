package neo.bank.carta.framework.adapter.output.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import neo.bank.carta.application.exceptions.CartaNonTrovataException;
import neo.bank.carta.application.ports.output.CartaOutputPort;
import neo.bank.carta.application.ports.output.CartaRepositoryPort;
import neo.bank.carta.application.ports.output.EventsPublisherPort;
import neo.bank.carta.domain.models.aggregates.Carta;
import neo.bank.carta.domain.models.events.EventPayload;
import neo.bank.carta.domain.models.vo.IdCarta;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CartaOutputAdapter  implements CartaOutputPort{

    private final CartaRepositoryPort ccRepo;
    private final EventsPublisherPort publisherPort;

    @Override
    public void salva(Carta cc) {

        List<EventPayload> events = cc.popChanges();
        ccRepo.save(cc.getIdCarta(), events);
        publisherPort.publish(Carta.AGGREGATE_NAME, cc.getIdCarta().getId(), events);
    }

    @Override
    public Carta recuperaDaId(IdCarta idCarta) {
        Carta cliente = ccRepo.findById(idCarta.getId());
        if(cliente == null) {
            throw new CartaNonTrovataException(idCarta);
        }
       return cliente;
    }
    
}
