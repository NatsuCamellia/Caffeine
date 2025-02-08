package net.natsucamellia.Caffeine.inquiry;

import net.natsucamellia.Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class InquiryCommand {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

    public void user(SlashCommandInteractionEvent event) {

        Member member = event.getOption("member").getAsMember();

        EmbedBuilder builder = this.getBuilder();

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

        event.replyEmbeds(builder.build()).queue();
    }

    public void guild(SlashCommandInteractionEvent event) {

        Guild guild = event.getGuild();

        EmbedBuilder builder = this.getBuilder();

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

        event.replyEmbeds(builder.build()).queue();
    }

    private long getOnlineCount(Guild guild) {
        OnlineStatus[] onlineStatus = {OnlineStatus.ONLINE, OnlineStatus.DO_NOT_DISTURB, OnlineStatus.IDLE};
        return guild.getMembers().stream()
                .filter((m) -> Arrays.asList(onlineStatus).contains(m.getOnlineStatus()))
                .count();
    }

    private EmbedBuilder getBuilder() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setTitle("資訊查詢");

        return builder;
    }
}
