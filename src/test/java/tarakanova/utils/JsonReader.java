package tarakanova.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * JsonReader provides utility methods for reading test data from JSON files.
 * Uses Gson library for JSON parsing and converts data into TestNG DataProvider format.
 *
 * This class enables data-driven testing by externalizing test data to JSON files,
 * making tests more maintainable and allowing easy addition of new test scenarios.
 *
 * @author Irina Tarakanova
 * @version 1.0
 */
public class JsonReader {
    private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);

    /**
     * Reads checkout validation test data from JSON file.
     * Parses JSON array and converts each object to a Map for TestNG DataProvider consumption.
     *
     * Expected JSON structure:
     * [
     *   {
     *     "firstName": "value",
     *     "lastName": "value",
     *     "postalCode": "value",
     *     "expectedErrorMessage": "value"
     *   }
     * ]
     *
     * @return Object[][] array suitable for TestNG DataProvider
     * @throws RuntimeException if JSON file is not found or parsing fails
     */
    public static Object[][] getCheckoutValidationData() {
        logger.info("Reading checkout validation data from JSON file");

        try {
            // Load JSON file from resources
            logger.debug("Loading checkoutValidationData.json from resources");
            InputStream is = JsonReader.class
                    .getClassLoader()
                    .getResourceAsStream("checkoutValidationData.json");

            if (is == null) {
                logger.error("checkoutValidationData.json file not found in resources");
                throw new RuntimeException("JSON file NOT FOUND in resources");
            }

            // Parse JSON array
            logger.debug("Parsing JSON data");
            JsonArray jsonArray = JsonParser.parseReader(new InputStreamReader(is))
                    .getAsJsonArray();

            logger.info("Found {} test data entries in JSON file", jsonArray.size());

            // Convert to TestNG DataProvider format
            Object[][] data = new Object[jsonArray.size()][1];

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject obj = jsonArray.get(i).getAsJsonObject();
                logger.debug("Processing test data entry {}", i + 1);

                // Convert JsonObject to Map
                Map<String, String> map = new HashMap<>();
                for (String key : obj.keySet()) {
                    String value = obj.get(key).getAsString();
                    map.put(key, value);
                    logger.debug("Added key-value pair: {} = {}", key, value);
                }

                data[i][0] = map;
                logger.debug("Completed processing entry {}", i + 1);
            }

            logger.info("Successfully loaded {} checkout validation test cases", data.length);
            return data;

        } catch (Exception e) {
            logger.error("Failed to read or parse JSON file: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }
}