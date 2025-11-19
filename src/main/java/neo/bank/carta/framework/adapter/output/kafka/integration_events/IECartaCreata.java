package neo.bank.carta.framework.adapter.output.kafka.integration_events;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Value;

@Value
public class IECartaCreata implements Serializable {
    private String idCarta;
    private String usernameCliente;
    private String numeroCarta;
    private String iban;
    private LocalDateTime dataEmissione;
    private LocalDateTime dataScadenza;
    private String intestatario;
}
