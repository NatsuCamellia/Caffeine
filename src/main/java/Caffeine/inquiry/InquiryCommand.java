package Caffeine.inquiry;

import Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class InquiryCommand {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

    public MessageEmbed user(User author, Member member) {

        EmbedBuilder builder = this.getBuilder(author);

        String account = member.getUser().getAsTag();
        builder.addField("帳號名稱", account, true);

        String thumbnail = member.getUser().getAvatarUrl() == null ? member.getUser().getDefaultAvatarUrl() : member.getUser().getAvatarUrl();
        builder.setThumbnail(thumbnail);

        String nickname = member.getEffectiveName();
        builder.addField("伺服器暱稱", nickname, true);

        String created = OffsetDateTime.ofInstant(member.getUser().getTimeCreated().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
        builder.addField("帳號創建時間", created, true);

        String joined = OffsetDateTime.ofInstant(member.getTimeJoined().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
        builder.addField("加入伺服器時間", joined, true);

        StringBuilder role = new StringBuilder();
        member.getRoles().forEach(r -> role.append(r.getAsMention()));
        builder.addField("身分組", role.toString(), true);

        return builder.build();
    }

    public MessageEmbed guild(Guild guild, User author) {

        EmbedBuilder builder = this.getBuilder(author);

        String guildName = guild.getName();
        builder.addField("伺服器名稱", guildName, true);

        String icon = guild.getIconUrl();
        builder.setThumbnail(icon);

        String created = OffsetDateTime.ofInstant(guild.getTimeCreated().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
        builder.addField("創建時間", created, true);

        String memberCount = guild.getMemberCount() + "人";
        builder.addField("成員人數", memberCount, true);

        long onlineCount = getOnlineCount(guild);
        builder.addField("線上人數", onlineCount + "人", true);

        return builder.build();
    }

    private long getOnlineCount(Guild guild) {
        OnlineStatus[] onlineStatus = {OnlineStatus.ONLINE, OnlineStatus.DO_NOT_DISTURB, OnlineStatus.IDLE};
        return guild.getMembers().stream()
                .filter((m) -> Arrays.asList(onlineStatus).contains(m.getOnlineStatus()))
                .count();
    }

    private EmbedBuilder getBuilder(User inquirer) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);

        builder.setAuthor("資訊查詢");

        String avatar = inquirer.getAvatarUrl() == null ? inquirer.getDefaultAvatarUrl() : inquirer.getAvatarUrl();
        builder.setFooter("查詢者：" + inquirer.getAsTag(), avatar);

        return builder;
    }
}
