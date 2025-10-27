package neo.bank.carta.domain.models.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DatiCartaView {
    
    private String numeroCarta;
    private String iban;
    private String intestatario;
    private LocalDate dataScadenza;
}
