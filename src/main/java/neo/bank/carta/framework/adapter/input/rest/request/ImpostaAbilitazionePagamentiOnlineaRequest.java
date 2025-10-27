package neo.bank.carta.framework.adapter.input.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImpostaAbilitazionePagamentiOnlineaRequest {
    private String numeroCarta;
    private String iban;
    private String usernameCliente;
    private boolean abilitazionePagamentiOnline;
}
