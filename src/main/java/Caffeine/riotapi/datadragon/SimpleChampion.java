package Caffeine.riotapi.datadragon;

import java.util.Set;

public class SimpleChampion {
    private String version;
    private String id;
    private String key;
    private String name;
    private String title;
    private String blurb;
    private Info info;
    private Image image;
    private Set<String> tags;
    private String partype;
    private Stat stats;

    public String getVersion() {
        return version;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getBlurb() {
        return blurb;
    }

    public Info getInfo() {
        return info;
    }

    public Image getImage() {
        return image;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getPartype() {
        return partype;
    }

    public Stat getStats() {
        return stats;
    }
}

class Info {
    private int attack;
    private int defense;
    private int magic;
    private int difficulty;
}

class Image {
    private String full;
    private String sprite;
    private String group;
    private int x;
    private int y;
    private int w;
    private int h;
}

