package neo.bank.carta.domain.models.enums;

public enum CodiceErrore {
    

    ID_NON_PUO_ESSERE_NULL("ID_NON_PUO_ESSERE_NULL"),
    USERNAME_CLIENTE_NON_PUO_ESSERE_NULL("USERNAME_CLIENTE_NON_PUO_ESSERE_NULL"),
    DATA_EMISSIONE_NON_PUO_ESSERE_NULL("DATA_EMISSIONE_NON_PUO_ESSERE_NULL"),
    DATA_SCADENZA_NON_PUO_ESSERE_NULL("DATA_SCADENZA_NON_PUO_ESSERE_NULL"),
    NUMERO_CARTA_NON_PUO_ESSERE_NULL("NUMERO_CARTA_NON_PUO_ESSERE_NULL"),
    IBAN_NON_PUO_ESSERE_NULL("IBAN_NON_PUO_ESSERE_NULL");

    private String codice;

    private CodiceErrore(String codice) {
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }
    
    
}
