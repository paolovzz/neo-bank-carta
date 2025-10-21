package neo.bank.carta.framework.adapter.input.rest.request;

import lombok.Value;

@Value
public class CreaCartaRequest {
    
    private String username;
    private String iban;
}
