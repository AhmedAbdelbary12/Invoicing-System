package com.invoicing.Utils.DataManagement;

import com.invoicing.Utils.Logs.LogsManager;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JSONReader {

    String jsonReader;
    String jsonFileName;
    String testDataPath = "src/test/resources/test-data/";

    public JSONReader(String jsonFileName) {
        this.jsonFileName = jsonFileName;
        try {
            JSONObject data = (JSONObject) new JSONParser().parse(new FileReader(testDataPath + jsonFileName + ".json"));
            jsonReader = data.toJSONString();
        } catch (Exception e) {
            LogsManager.error("Error reading JSON file" + jsonFileName + e.getMessage());
            jsonReader = "{}";
        }
    }

    public String getJsonData(String jsonPath) {
        try {
            return JsonPath.read(jsonReader, jsonPath);
        } catch (Exception e) {
            LogsManager.error("Error getting JSON DATA");
            return "";

        }

    }
}
