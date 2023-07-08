package Caffeine.riotapi;

import Caffeine.riotapi.datadragon.DDragonDataFetcher;
import Caffeine.riotapi.datadragon.SimpleChampion;
import Caffeine.riotapi.dto.ChampionMasteryDTO;
import Caffeine.riotapi.dto.LeagueEntryDTO;
import Caffeine.riotapi.dto.SummonerDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class RiotDataFetcher {
    private final String TW2_URL_BASE = "https://tw2.api.riotgames.com";
    private String API_KEY;
    private final String SUMMONER_V4 = "/lol/summoner/v4/summoners/by-name/%s?api_key=%s"; // Input : Summoner name
    private final String LEAGUE_V4 = "/lol/league/v4/entries/by-summoner/%s?api_key=%s"; // Input : Encrypted Summoner id
    private final String CHAMPION_MASTERY = "/lol/champion-mastery/v4/champion-masteries/by-summoner/%s?api_key=%s"; // Input : Encrypted Summoner id
    private final String CHAMPION_MASTERY_TOP = "/lol/champion-mastery/v4/champion-masteries/by-summoner/%s/top?count=%d&api_key=%s"; // Input : Encrypted Summoner id, count

    DDragonDataFetcher dragonDataFetcher = new DDragonDataFetcher();
    Gson gson = new Gson();
    private Map<String, SimpleChampion> championMap;

    public SummonerDTO getSummonerByName(String summonerName) {
        try {
            URL url = new URL(TW2_URL_BASE + String.format(SUMMONER_V4, summonerName, API_KEY));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            SummonerDTO summoner = gson.fromJson(reader, SummonerDTO.class);
            reader.close();
            connection.disconnect();

            return summoner;
        } catch (FileNotFoundException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Set<LeagueEntryDTO> getLeagueBySummonerId(String summonerId) {
        try {
            URL url = new URL(TW2_URL_BASE + String.format(LEAGUE_V4, summonerId, API_KEY));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            Set<LeagueEntryDTO> league = gson.fromJson(reader, new TypeToken<HashSet<LeagueEntryDTO>>(){}.getType());
            reader.close();
            connection.disconnect();
            return league;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ChampionMasteryDTO> getMasteryBySummonerId(String summonerId) {
        try {
            URL url = new URL(TW2_URL_BASE + String.format(CHAMPION_MASTERY, summonerId, API_KEY));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            List<ChampionMasteryDTO> mastery = gson.fromJson(reader, new TypeToken<LinkedList<ChampionMasteryDTO>>(){}.getType());
            reader.close();
            connection.disconnect();
            return mastery;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ChampionMasteryDTO> getMasteryTopBySummonerId(String summonerId, int count) {
        try {
            URL url = new URL(TW2_URL_BASE + String.format(CHAMPION_MASTERY_TOP, summonerId, count, API_KEY));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            List<ChampionMasteryDTO> mastery = gson.fromJson(reader, new TypeToken<LinkedList<ChampionMasteryDTO>>(){}.getType());
            reader.close();
            connection.disconnect();
            return mastery;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ChampionMasteryDTO> getMasteryTopBySummonerId(String summonerId) {
        return getMasteryTopBySummonerId(summonerId, 5);
    }

    private void updateChampionMap() {
        if (championMap == null) championMap = dragonDataFetcher.getChampionMap();
    }

    public String getChampionNameById(long id) {
        String championId = String.valueOf(id);
        updateChampionMap();
        for (SimpleChampion champion : championMap.values()) {
            if (champion.getKey().equals(championId)) return champion.getName();
        }

        return null;
    }

    public void setAPI_KEY(String apiKey) {
        this.API_KEY = apiKey;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }
}
