package neo.bank.carta.framework.adapter.output.mongodb.entities;

import org.bson.codecs.pojo.annotations.BsonId;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MongoEntity(collection="numero-carta-projection")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class NumeroCartaProjectionEntity extends PanacheMongoEntityBase {

    @BsonId
    private String numeroCarta;
    private String idCarta;

    public NumeroCartaProjectionEntity(String numeroCarta, String idCarta) {
        this.numeroCarta = numeroCarta;
        this.idCarta = idCarta;
    }
    
}