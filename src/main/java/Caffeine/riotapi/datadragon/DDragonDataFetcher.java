package Caffeine.riotapi.datadragon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DDragonDataFetcher {
    private final String VERSION = "https://ddragon.leagueoflegends.com/api/versions.json";
    private final String CHAMPION = "https://ddragon.leagueoflegends.com/cdn/%s/data/zh_TW/champion.json";

    private Gson gson = new Gson();

    public List<String> getVersionList() {
        try {
            URL url = new URL(VERSION);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            List<String> version = gson.fromJson(reader, new TypeToken<ArrayList<String>>(){}.getType());
            reader.close();
            connection.disconnect();

            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getCurrentVersion() {
        return getVersionList().get(0);
    }

    public Map<String, SimpleChampion> getChampionMap() {
        try {
            URL url = new URL(String.format(CHAMPION, getCurrentVersion()));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            Champion champion = gson.fromJson(reader, Champion.class);
            reader.close();
            connection.disconnect();

            return champion.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
