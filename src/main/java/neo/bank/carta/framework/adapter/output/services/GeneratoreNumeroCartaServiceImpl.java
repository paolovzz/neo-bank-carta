package neo.bank.carta.framework.adapter.output.services;

import java.util.Random;

import jakarta.enterprise.context.ApplicationScoped;
import neo.bank.carta.domain.models.vo.NumeroCarta;
import neo.bank.carta.domain.services.GeneratoreNumeroCartaService;

@ApplicationScoped
public class GeneratoreNumeroCartaServiceImpl implements GeneratoreNumeroCartaService {

    @Override
    public NumeroCarta genera() {
        String prefix = "4000"; // prefisso esempio, puoi cambiarlo per simulare diverse banche
        int cardLength = 16;
        String fakeCardNumber = generaNumeroCarta(prefix, cardLength);
        return new NumeroCarta(fakeCardNumber);
    }

    public String generaNumeroCarta(String prefix, int length) {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder(prefix);

        // Genera numeri casuali per completare fino alla penultima cifra
        while (cardNumber.length() < length - 1) {
            int digit = random.nextInt(10);
            cardNumber.append(digit);
        }
        // Calcola cifra di controllo con algoritmo di Luhn
        int checkDigit = calcolaCifraDiControllo(cardNumber.toString());
        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }

    // Calcolo della cifra di controllo secondo algoritmo di Luhn
    public int calcolaCifraDiControllo(String number) {
        int sum = 0;
        boolean alternate = true;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        int mod = sum % 10;
        return (mod == 0) ? 0 : 10 - mod;
    }
    
}
