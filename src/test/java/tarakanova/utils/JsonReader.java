package tarakanova.utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class JsonReader {

    public static Object[][] getCheckoutValidationData() {

        try {
            InputStream is = JsonReader.class
                    .getClassLoader()
                    .getResourceAsStream("checkoutValidationData.json");



            if (is == null) {
                throw new RuntimeException("JSON file NOT FOUND in resources");
            }

            JsonArray jsonArray = JsonParser.parseReader(new InputStreamReader(is))
                    .getAsJsonArray();

            Object[][] data = new Object[jsonArray.size()][1];

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject obj = jsonArray.get(i).getAsJsonObject();

                Map<String, String> map = new HashMap<>();

                for (String key : obj.keySet()) {
                    map.put(key, obj.get(key).getAsString());
                }

                data[i][0] = map;
            }

            return data;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }
}