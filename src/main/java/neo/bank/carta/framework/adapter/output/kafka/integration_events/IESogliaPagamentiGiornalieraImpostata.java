package neo.bank.carta.framework.adapter.output.kafka.integration_events;

import java.io.Serializable;

import lombok.Value;

@Value
public class IESogliaPagamentiGiornalieraImpostata implements Serializable {
    private int sogliaPagamentiGiornaliera;
}
