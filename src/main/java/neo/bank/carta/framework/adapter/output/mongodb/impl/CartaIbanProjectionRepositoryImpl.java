package neo.bank.carta.framework.adapter.output.mongodb.impl;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.application.ports.output.CartaIbanProjectionRepositoryPort;
import neo.bank.carta.domain.models.vo.DataScadenza;
import neo.bank.carta.domain.models.vo.DatiCartaView;
import neo.bank.carta.domain.models.vo.Iban;
import neo.bank.carta.domain.models.vo.IntestatarioCarta;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.models.vo.UsernameCliente;
import neo.bank.carta.framework.adapter.output.mongodb.entities.CartaIbanProjectionEntity;

@ApplicationScoped
@Slf4j
public class CartaIbanProjectionRepositoryImpl implements PanacheMongoRepositoryBase<CartaIbanProjectionEntity, String>, CartaIbanProjectionRepositoryPort {
    
    
    @Override
    public List<DatiCartaView> recuperaDaIbanEIntestatario(Iban iban, UsernameCliente usernameCliente) {
        
        List<CartaIbanProjectionEntity> listaNumeroCarta = find("iban=?1 AND usernameCliente=?2", iban.codice(), usernameCliente.username()).list();
        if(listaNumeroCarta != null && !listaNumeroCarta.isEmpty() ) {
            return listaNumeroCarta.stream().map(entity -> {
                DatiCartaView view = new DatiCartaView();
                view.setDataScadenza(entity.getDataScadenza());
                view.setIban(entity.getIban());
                view.setIntestatario(entity.getIntestatario());
                view.setNumeroCarta(entity.getNumeroCarta());
                return view;
            }).toList();
        }
        else  {
            return new ArrayList<>();
        }
    }

    @Override
    public void salva(NumeroCarta numeroCarta, Iban iban, IntestatarioCarta intestatarioCarta,
            DataScadenza dataScadenza, UsernameCliente usernameCliente) {
        log.info("Aggiorno la projection...");
        persist(new CartaIbanProjectionEntity(numeroCarta.numero(), iban.codice(), intestatarioCarta.intestatario(), dataScadenza.dataOra().toLocalDate(), usernameCliente.username()));
    }
    
}
