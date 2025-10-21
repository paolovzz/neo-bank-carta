package neo.bank.carta.domain.models.vo;

import java.time.LocalDateTime;

import neo.bank.carta.domain.exceptions.ValidazioneException;
import neo.bank.carta.domain.models.enums.CodiceErrore;

public record DataEmissione(LocalDateTime dataOra) {


    public DataEmissione(LocalDateTime dataOra) {
        if (dataOra == null) {
            throw new ValidazioneException(DataEmissione.class.getSimpleName(), CodiceErrore.DATA_EMISSIONE_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.dataOra = dataOra;
    }
}
