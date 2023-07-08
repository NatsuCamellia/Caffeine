package Caffeine.riotapi.dto;

public class ChampionMasteryDTO {
    private String puuid;
    private long championPointsUntilNextLevel;
    private boolean chestGranted;

    public String getPuuid() {
        return puuid;
    }

    public long getChampionPointsUntilNextLevel() {
        return championPointsUntilNextLevel;
    }

    public boolean isChestGranted() {
        return chestGranted;
    }

    public long getChampionId() {
        return championId;
    }

    public long getLastPlayTime() {
        return lastPlayTime;
    }

    public int getChampionLevel() {
        return championLevel;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public int getChampionPoints() {
        return championPoints;
    }

    public long getChampionPointsSinceLastLevel() {
        return championPointsSinceLastLevel;
    }

    public int getTokensEarned() {
        return tokensEarned;
    }

    private long championId;
    private long lastPlayTime;
    private int championLevel;
    private String summonerId;
    private int championPoints;
    private long championPointsSinceLastLevel;
    private int tokensEarned;
}
