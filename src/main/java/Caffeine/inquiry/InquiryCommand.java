package Caffeine.inquiry;

import Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InquiryCommand {

    public void guild(Guild guild, TextChannel channel, User author) {
        String guildTag = guild.getName();
        String icon = guild.getIconUrl();
        String avatar = author.getAvatarUrl() == null ? author.getDefaultAvatarUrl() : author.getAvatarUrl();
        String guildName = guild.getName();
        String memberCount = guild.getMemberCount() + "人";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        String created = OffsetDateTime.ofInstant(guild.getTimeCreated().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setAuthor(guildTag);
        builder.setThumbnail(icon);
        builder.setFooter("查詢者：" + author.getAsTag(), avatar);
        builder.addField("伺服器名稱", guildName, true);
        builder.addField("創建時間", created, true);
        builder.addField("成員人數", memberCount, true);

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
