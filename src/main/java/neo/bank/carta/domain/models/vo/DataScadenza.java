package neo.bank.carta.domain.models.vo;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import neo.bank.carta.domain.exceptions.ValidazioneException;
import neo.bank.carta.domain.models.enums.CodiceErrore;

@Getter
@EqualsAndHashCode
public class DataScadenza {

    private LocalDateTime dataOra;

    public DataScadenza(LocalDateTime dataOra) {
        if (dataOra == null) {
            throw new ValidazioneException(DataScadenza.class.getSimpleName(),
                    CodiceErrore.DATA_SCADENZA_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.dataOra = dataOra;
    }
}
