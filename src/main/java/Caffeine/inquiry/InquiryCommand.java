package Caffeine.inquiry;

import Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class InquiryCommand {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

    public void user(TextChannel channel, User author, Message message) {

        Member member;

        try {
            member = message.getMentions().getMembers().get(0);
        } catch (IndexOutOfBoundsException e) {
            member = message.getMember();
        }

        EmbedBuilder builder = this.getBuilder(author);

        String account = member.getUser().getAsTag();
        builder.addField("帳號名稱", account, true);

        String avatar = member.getUser().getAvatarUrl() == null ? member.getUser().getDefaultAvatarUrl() : member.getUser().getAvatarUrl();
        builder.setThumbnail(avatar);

        String nickname = member.getEffectiveName();
        builder.addField("伺服器暱稱", nickname, true);

        String created = OffsetDateTime.ofInstant(member.getUser().getTimeCreated().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
        builder.addField("帳號創建時間", created, true);

        String joined = OffsetDateTime.ofInstant(member.getTimeJoined().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
        builder.addField("加入伺服器時間", joined, true);

        Iterator<Role> roleIterator = member.getRoles().iterator();
        String role = "";
        while (roleIterator.hasNext()) role += roleIterator.next().getAsMention();
        builder.addField("身分組", role, true);

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    public void guild(Guild guild, TextChannel channel, User author) {

        EmbedBuilder builder = this.getBuilder(author);

        String guildName = guild.getName();
        builder.addField("伺服器名稱", guildName, true);

        String icon = guild.getIconUrl();
        builder.setThumbnail(icon);

        String created = OffsetDateTime.ofInstant(guild.getTimeCreated().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
        builder.addField("創建時間", created, true);

        String memberCount = guild.getMemberCount() + "人";
        builder.addField("成員人數", memberCount, true);

        channel.sendMessageEmbeds(builder.build()).queue();
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
