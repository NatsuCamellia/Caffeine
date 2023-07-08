package Caffeine.riotapi.dto;

public class LeagueEntryDTO {
    private String leagueId;
    private String summonerId; // Player's encrypted summonerId.
    private String summonerName;
    private String queueType;
    private String tier;
    private String rank; // The player's division within a tier.
    private int leaguePoints;
    private int wins; // Winning team on Summoners Rift.
    private int losses; // Losing team on Summoners Rift.
    private boolean hotStreak;
    private boolean veteran;
    private boolean freshBlood;
    private boolean inactive;
    private MiniSeriesDTO miniSeries;

    public String getLeagueId() {
        return leagueId;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public String getQueueType() {
        return queueType;
    }

    public String getTier() {
        return tier;
    }

    public String getRank() {
        return rank;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public boolean isHotStreak() {
        return hotStreak;
    }

    public boolean isVeteran() {
        return veteran;
    }

    public boolean isFreshBlood() {
        return freshBlood;
    }

    public boolean isInactive() {
        return inactive;
    }

    public MiniSeriesDTO getMiniSeries() {
        return miniSeries;
    }
}
