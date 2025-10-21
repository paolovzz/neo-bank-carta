package neo.bank.carta.framework.adapter.input.rest.request;

import lombok.Value;

@Value
public class ImpostaStatoCartaRequest {
    private String numeroCarta;
    private String iban;
    private String usernameCliente;
    private boolean statoCarta;
}
