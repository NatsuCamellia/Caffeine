package Caffeine.weather;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CWBDataFetcher {
    private String apiKey;
    private String apiUrl;
    private JsonObject jsonObject;

    public void setDataset(Dataset dataset) {
        apiUrl = String.format("https://opendata.cwb.gov.tw/api/v1/rest/datastore/%s?Authorization=%s", dataset.label, apiKey);
    }

    public void fetch() {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();
            Gson gson = new Gson();
            jsonObject = gson.fromJson(response.toString(), JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JsonArray getLocationArray() {
        return jsonObject.get("records").getAsJsonObject().get("location").getAsJsonArray();
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
