package neo.bank.carta.framework.adapter.output.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteInfoResponse {
   
    private String nome;
    private String cognome;


    

}
