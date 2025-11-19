package neo.bank.carta.framework.adapter.output.mongodb.impl;

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.framework.adapter.output.mongodb.entities.NumeroCartaProjectionEntity;
import neo.bank.carta.application.ports.output.NumeroCartaProjectionRepositoryPort;
import neo.bank.carta.domain.models.vo.IdCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;

@ApplicationScoped
@Slf4j
public class NumeroCartaProjectionRepositoryImpl implements PanacheMongoRepositoryBase<NumeroCartaProjectionEntity, String>, NumeroCartaProjectionRepositoryPort {
    
    
    @Override
    public void salva(NumeroCarta numeroCarta, IdCarta idCarta) {
        log.info("Aggiorno la projection...");
        persist(new NumeroCartaProjectionEntity(numeroCarta.getNumero(), idCarta.getId()));
    }

    @Override
    public IdCarta recuperaDaNumeroCarta(NumeroCarta numeroCarta) {
       NumeroCartaProjectionEntity entity = findById(numeroCarta.getNumero());
        return entity == null ? null : new IdCarta(entity.getIdCarta());
    }
}
