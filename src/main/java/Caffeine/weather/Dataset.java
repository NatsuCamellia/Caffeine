package Caffeine.weather;

public enum Dataset {
    ALL_36HR("F-C0032-001");

    public final String label;

    Dataset(String label) {
        this.label = label;
    }
}
