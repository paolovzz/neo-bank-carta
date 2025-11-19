package neo.bank.carta.domain.services;

import neo.bank.carta.domain.models.vo.IntestatarioCarta;
import neo.bank.carta.domain.models.vo.UsernameCliente;

public interface AnagraficaClienteService {
    
    IntestatarioCarta recuperaDatiIntestatario(UsernameCliente usernameCliente);
}
