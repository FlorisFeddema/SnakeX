package snakex.shared;

import com.google.gson.JsonObject;

public class Static {

    private Static(){

    }

    public static boolean keyInJson(JsonObject json, String key) {
        try {
            return json.has(key);
        } catch (NullPointerException ex) {
            return false;
        }
    }

}
