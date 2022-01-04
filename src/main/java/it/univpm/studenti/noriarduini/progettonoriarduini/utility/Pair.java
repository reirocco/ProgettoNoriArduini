package it.univpm.studenti.noriarduini.progettonoriarduini.utility;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class Pair<K, V> {
    private Map<K, V> map = new HashMap<K, V>();

    public V get(K key) {
        return map.get(key);
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public boolean hasKey(K key) {
        return map.containsKey(key);
    }

    public JSONArray getJSONArrayFromKey(K key) {
        return new JSONArray(map.get(key));
    }

}
