package neo.bank.carta.domain.models.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import neo.bank.carta.domain.exceptions.ValidazioneException;
import neo.bank.carta.domain.models.enums.CodiceErrore;

@Getter
@EqualsAndHashCode
public class IntestatarioCarta {
    private String intestatario;

    public IntestatarioCarta(String intestatario) {
        if (intestatario == null || intestatario.isBlank()) {
            throw new ValidazioneException(
                    IntestatarioCarta.class.getSimpleName(),
                    CodiceErrore.INTESTATARIO_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.intestatario = intestatario;
    }
}
