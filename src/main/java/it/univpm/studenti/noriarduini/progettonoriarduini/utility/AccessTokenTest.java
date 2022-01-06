package it.univpm.studenti.noriarduini.progettonoriarduini.utility;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.service.Request;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpClientErrorException;

public class AccessTokenTest {
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

    public static boolean tokenIsValid() {
        JSONObject testObj = AccessTokenTest.testTokenValidity();
        String validity = testObj.getString("access_token");

        return validity.equals("VALID");
    }
}
