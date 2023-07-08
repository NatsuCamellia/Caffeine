package Caffeine.riotapi.datadragon;

import java.util.Map;

public class Champion {
    private String type;
    private String format;
    private String version;
    private Map<String, SimpleChampion> data;

    public Map<String, SimpleChampion> getData() {
        return data;
    }
}
