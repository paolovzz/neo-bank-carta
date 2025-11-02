package neo.bank.carta.application.ports.input.dto;

import lombok.Value;
import neo.bank.carta.domain.models.vo.Iban;
import neo.bank.carta.domain.models.vo.UsernameCliente;

@Value
public class RecuperaCartaDaIbanCmd {
    
    private UsernameCliente usernameCliente;
    private Iban iban;
}
