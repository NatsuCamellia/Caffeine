package Caffeine.util;
import Caffeine.core.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class EmbedUtil {

    public final static int BLUE = 0x5398ed;
    public final static int RED = 0xFF0000;

    public static void sendErrorUsageEmbed (TextChannel channel, User author, String command, String description) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.RED);

        builder.setTitle("指令錯誤：" + Bot.prefix + command);
        builder.setDescription(description);

        String avatar = author.getAvatarUrl() == null ? author.getDefaultAvatarUrl() : author.getAvatarUrl();
        builder.setFooter(author.getAsTag(), avatar);

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
