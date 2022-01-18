package it.univpm.studenti.noriarduini.progettonoriarduini.utility;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.service.Request;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpClientErrorException;

/**
 * AccesssTokenValidator class
 *
 * <p>
 * <p>
 * la classe testa la validità del token inserito nel file config.json
 *
 * @author Rocco Nori
 * @author Federico Arduini
 * @version 1.0
 * @since 2022-01-16
 */
public class AccessTokenValidator {

    /**
     * il metodo effettua una chiamata http per testare la validità del token ritorna un json con "VALID" o "EXPIRED_OR_INVALID" a seconda della validità del token
     *
     * @return JSONObject json con la risposta
     */
    public static JSONObject testTokenValidity() {
        Request request = new Request(new RestTemplateBuilder());
        String response;
        JSONObject result = new JSONObject();

        try {
            response = request.plainTextGetRequest("https://graph.facebook.com/me?access_token=" + ProgettoNoriArduiniApplication.conf.getAccessToken());
            result.put("access_token", "VALID");
        } catch (HttpClientErrorException e) {
            response = e.getResponseBodyAsString();
            result = new JSONObject(response);
            result.put("access_token", "EXPIRED_OR_INVALID");
        }

        return result;
    }

    /**
     * il metodo ritorna un booleano
     * <ul>
     *     <li><b>true</b> --> token valido</li>
     *     <li><b>false</b> --> token non valido</li>
     * </ul>
     *
     * @return
     */
    public static boolean tokenIsValid() {
        JSONObject testObj = AccessTokenValidator.testTokenValidity();
        String validity = testObj.getString("access_token");

        return validity.equals("VALID");
    }
}
