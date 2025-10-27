package neo.bank.carta.framework.adapter.output.mongodb.entities;

import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonId;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MongoEntity(collection="carta-iban-projection")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CartaIbanProjectionEntity extends PanacheMongoEntityBase {

    @BsonId
    private String numeroCarta;
    private String iban;
    private String intestatario;
    private LocalDate dataScadenza;

    public CartaIbanProjectionEntity(String numeroCarta, String iban, String intestatario, LocalDate dataScadenza) {
        this.numeroCarta = numeroCarta;
        this.iban = iban;
        this.intestatario = intestatario;
        this.dataScadenza = dataScadenza;
    }
    
}