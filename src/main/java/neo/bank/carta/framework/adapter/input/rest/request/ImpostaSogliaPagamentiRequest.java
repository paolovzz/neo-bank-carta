package neo.bank.carta.framework.adapter.input.rest.request;

import lombok.Value;

@Value
public class ImpostaSogliaPagamentiRequest {
    private String numeroCarta;
    private String iban;
    private String usernameCliente;
    private int nuovaSoglia;
}
