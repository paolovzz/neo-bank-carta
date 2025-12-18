package neo.bank.carta.application.ports.input.dto;

import lombok.Value;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.models.vo.SogliaPagamenti;
import neo.bank.carta.domain.models.vo.UsernameCliente;

@Value
public class ImpostaSogliaPagamentiCmd {
    
    private NumeroCarta numeroCarta;
    private UsernameCliente usernameCliente;
    private SogliaPagamenti nuovaSogliaPagamenti;
}
