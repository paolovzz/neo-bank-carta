package neo.bank.carta.domain.models.vo;

import java.time.LocalDateTime;

import neo.bank.carta.domain.exceptions.ValidazioneException;
import neo.bank.carta.domain.models.enums.CodiceErrore;

public record DataCreazione(LocalDateTime dataOra) {


    public DataCreazione(LocalDateTime dataOra) {
        if (dataOra == null) {
            throw new ValidazioneException(DataCreazione.class.getSimpleName(), CodiceErrore.DATA_CREAZINOE_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.dataOra = dataOra;
    }
}
