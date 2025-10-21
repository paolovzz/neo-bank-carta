package neo.bank.carta.domain.models.vo;

import neo.bank.carta.domain.exceptions.ValidazioneException;
import neo.bank.carta.domain.models.enums.CodiceErrore;

public record IdCarta(String id) {
    public IdCarta {
        if (id == null) {
            throw new ValidazioneException(
                IdCarta.class.getSimpleName(),
                CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice()
            );
        }
    }
}
