package neo.bank.carta.application.ports.input.dto;

import lombok.Value;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.models.vo.UsernameCliente;

@Value
public class RecuperaCartaDaNumeroCmd {
    
    private UsernameCliente usernameCliente;
    private NumeroCarta numeroCarta;
}
