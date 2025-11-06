package neo.bank.carta.domain.models.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import neo.bank.carta.domain.exceptions.ValidazioneException;
import neo.bank.carta.domain.models.enums.CodiceErrore;

@Getter
@EqualsAndHashCode
public class IdCarta {
    private String id;

    public IdCarta(String id) {
        if (id == null) {
            throw new ValidazioneException(
                    IdCarta.class.getSimpleName(),
                    CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.id = id;
    }
}
