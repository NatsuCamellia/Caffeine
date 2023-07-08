package Caffeine.weather;

import com.google.gson.JsonArray;

public class Weather36HR {
    public final String locationName;
    private String[] startTime;
    private String[] endTime;
    private String[] wx;
    private String[] pop;
    private String[] minT;
    private String[] ci;
    private String[] maxT;
    public final int length;
    public Weather36HR(JsonArray jsonArray, String locationName) {
        JsonArray wxArray = jsonArray.get(0).getAsJsonObject().get("time").getAsJsonArray();
        JsonArray popArray = jsonArray.get(1).getAsJsonObject().get("time").getAsJsonArray();
        JsonArray minTArray = jsonArray.get(2).getAsJsonObject().get("time").getAsJsonArray();
        JsonArray ciArray = jsonArray.get(3).getAsJsonObject().get("time").getAsJsonArray();
        JsonArray maxTArray = jsonArray.get(4).getAsJsonObject().get("time").getAsJsonArray();

        this.locationName = locationName;
        length = wxArray.size();
        startTime = new String[length];
        endTime = new String[length];
        wx = new String[length];
        pop = new String[length];
        minT = new String[length];
        ci = new String[length];
        maxT = new String[length];

        for (int i = 0; i < length; i++) {
            startTime[i] = wxArray.get(i).getAsJsonObject().get("startTime").getAsString();
            endTime[i] = wxArray.get(i).getAsJsonObject().get("endTime").getAsString();
            wx[i] = getParameterName(wxArray, i);
            pop[i] = getParameterName(popArray, i);
            minT[i] = getParameterName(minTArray, i);
            ci[i] = getParameterName(ciArray, i);
            maxT[i] = getParameterName(maxTArray, i);
        }
    }

    private String getParameterName(JsonArray time, int i) {
        return time.get(i).getAsJsonObject().get("parameter").getAsJsonObject().get("parameterName").getAsString();
    }

    public String getTime(int i) {
        return startTime[i].substring(11, startTime[i].length() - 3) + "~" + endTime[i].substring(11, endTime[i].length() - 3);
    }

//    public String getCI(int i) {
//
//    }
    public String getTemp(int i) {
        return String.format("%s° - %s°", minT[i], maxT[i]);
    }

    public String getPoP(int i) {
        return String.format("%s%%", pop[i]);
    }
}
