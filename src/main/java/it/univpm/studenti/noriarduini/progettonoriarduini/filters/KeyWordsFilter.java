package it.univpm.studenti.noriarduini.progettonoriarduini.filters;

import it.univpm.studenti.noriarduini.progettonoriarduini.ProgettoNoriArduiniApplication;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Feed;
import it.univpm.studenti.noriarduini.progettonoriarduini.model.Post;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KeyWordsFilter implements Filter {

    @Override
    public boolean check(JSONObject requestBody) {
        boolean res = false;
        if (requestBody.has("keywords")) {
            res = true;
        }
        return res;
    }

    @Override
    public Feed filter(JSONObject requestBody, Feed feed) {
        ArrayList<String> s;
        if (requestBody.has("keywords")) {
            JSONArray array = requestBody.getJSONArray("keywords");
            // converto il JSONArray in ArrayList
            if (!array.isEmpty()) {
                s = new ArrayList<String>();
                for (int i = 0; i < array.length(); i++) {
                    s.add(array.getString(i));
                }
            } else {
                s = ProgettoNoriArduiniApplication.conf.getDataSet();
            }
            Feed res = new Feed();
            for (int i = 0; i < feed.getTotalPost(); i++) {

                Post p = feed.getSinglePost(i);

                if (p.hasKeyWords(s)) {
                    res.addPost(p);
                }


            }
            return res;
        }
        return new Feed();
    }

}
