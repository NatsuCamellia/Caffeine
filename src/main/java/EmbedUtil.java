import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class EmbedUtil {

    public static int blue = 0x5398ed;

    public static void sendUsageEmbed(TextChannel channel, String title, String description) {
        EmbedBuilder usage = new EmbedBuilder();
        usage.setColor(EmbedUtil.blue);
        usage.setTitle(title);
        usage.setDescription(String.format("用法：`%s`", description));
        channel.sendMessageEmbeds(usage.build()).queue();
    }

    public static void sendMessageEmbed(TextChannel channel, String title, String message, User user) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.blue);
        builder.setTitle(title);
        builder.setDescription(message);
        builder.setFooter(user.getAsTag(), user.getAvatarUrl());
        channel.sendMessageEmbeds(builder.build()).queue();
    }

}
