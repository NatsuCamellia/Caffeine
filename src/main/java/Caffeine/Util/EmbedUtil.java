package Caffeine.Util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class EmbedUtil {

    public final static int BLUE = 0x5398ed;
    public final static int GREEN = 0x0b8a00;
    public final static int RED = 0xFF0000;

    public static void sendUsageEmbed(TextChannel channel, String title, String description) {
        EmbedBuilder usage = new EmbedBuilder();
        usage.setColor(EmbedUtil.RED);
        usage.setTitle(title);
        usage.setDescription(String.format("指令: `%s`", description));
        channel.sendMessageEmbeds(usage.build()).queue();
    }

    public static void sendMessageEmbed(TextChannel channel, String title, String message, User user) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setTitle(title);
        builder.setDescription(message);
        builder.setAuthor(user.getAsTag());
        builder.setThumbnail(user.getAvatarUrl());
        channel.sendMessageEmbeds(builder.build()).queue();
    }

}
