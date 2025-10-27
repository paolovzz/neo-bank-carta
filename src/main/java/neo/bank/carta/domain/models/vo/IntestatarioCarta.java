package neo.bank.carta.domain.models.vo;

import neo.bank.carta.domain.exceptions.ValidazioneException;
import neo.bank.carta.domain.models.enums.CodiceErrore;

public record IntestatarioCarta(String intestatario) {
    public IntestatarioCarta {
        if (intestatario == null) {
            throw new ValidazioneException(
                IntestatarioCarta.class.getSimpleName(),
                CodiceErrore.INTESTATARIO_NON_PUO_ESSERE_NULL.getCodice()
            );
        }
    }
}
