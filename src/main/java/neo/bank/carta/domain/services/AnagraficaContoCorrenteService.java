package neo.bank.carta.domain.services;

import neo.bank.carta.domain.models.vo.Iban;
import neo.bank.carta.domain.models.vo.UsernameCliente;

public interface AnagraficaContoCorrenteService {
    
    boolean richiediVerificaContoCorrente(UsernameCliente usernameCliente, Iban iban);
}
