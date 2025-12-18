package neo.bank.carta.domain.models.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import neo.bank.carta.domain.exceptions.ValidazioneException;
import neo.bank.carta.domain.models.enums.CodiceErrore;


@Getter
@EqualsAndHashCode
public class SogliaPagamenti {

    private int soglia;

    public SogliaPagamenti(int soglia) {
        if (soglia <= 0) {
            throw new ValidazioneException(
                SogliaPagamenti.class.getSimpleName(),
                CodiceErrore.SOGLIA_PAGAMENTI_NON_PUO_ESSERE_MINORE_UGUALE_A_ZERO.getCodice()
            );
        }
        this.soglia = soglia;
    }
}
