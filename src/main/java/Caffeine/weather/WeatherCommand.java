package Caffeine.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class WeatherCommand {
    CWBDataFetcher fetcher = new CWBDataFetcher();
    public void weather36HR(SlashCommandInteractionEvent event) {
        fetcher.setDataset(Dataset.ALL_36HR);
        fetcher.fetch();

        JsonArray locationArray = fetcher.getLocationArray();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("今明 36 小時天氣預報");
        for (JsonElement element : locationArray) {
            Weather36HR weather = new Weather36HR(
                    element.getAsJsonObject().get("weatherElement").getAsJsonArray(),
                    element.getAsJsonObject().get("locationName").getAsString());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < weather.length; i++) {
                sb.append(String.format(":clock3: %s\n", weather.getTime(i)));
                sb.append(String.format(":thermometer: %s\n", weather.getTemp(i)));
                sb.append(String.format(":droplet: %s\n", weather.getPoP(i)));
                sb.append("\n");
            }

            builder.addField(weather.locationName, sb.toString(), true);
        }

        event.replyEmbeds(builder.build()).queue();
    }

    public WeatherCommand(String apiKey) {
        fetcher.setApiKey(apiKey);
    }
}
