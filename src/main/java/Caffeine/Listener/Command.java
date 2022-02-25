package Caffeine.listener;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import Caffeine.data.Userdata;
import Caffeine.util.EmbedUtil;
import Caffeine.util.MySqlUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class Command {

    public void user(Guild guild, TextChannel channel, User author, Message message) {
        
        Member member;

        try {
            member = message.getMentionedMembers().get(0);
        } catch (IndexOutOfBoundsException e) {
            member = message.getMember();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

        String account = member.getUser().getAsTag();
        String nickname = member.getEffectiveName();
        String avatar = member.getUser().getAvatarUrl() == null ? member.getUser().getDefaultAvatarUrl() : member.getUser().getAvatarUrl();
        String created = OffsetDateTime.ofInstant(member.getUser().getTimeCreated().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
        String joined = OffsetDateTime.ofInstant(member.getTimeJoined().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
        MySqlUtil mySqlUtil = new MySqlUtil();
        Userdata userdata = mySqlUtil.getUserdata(member.getId());
        Integer balance = userdata.getBalance();
        Iterator<Role> roleIterator = member.getRoles().iterator();
        String role = "";
        while (roleIterator.hasNext()) role += roleIterator.next().getAsMention();

        String authorAvatar = author.getAvatarUrl() == null ? author.getDefaultAvatarUrl() : author.getAvatarUrl();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setAuthor(account);
        builder.setThumbnail(avatar);
        builder.setFooter("查詢者：" + author.getAsTag(), authorAvatar);
        builder.addField("帳號名稱", account, true);
        builder.addField("伺服器暱稱", nickname, true);
        builder.addField("帳號創建時間", created, true);
        builder.addField("加入伺服器時間", joined, true);
        builder.addField("金錢", String.valueOf(balance), true);
        builder.addField("身分組", role, true);

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    public void help(TextChannel channel, User author) {
        
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setFooter(author.getAsTag(), author.getAvatarUrl());
        builder.setAuthor("Caffeine 指令清單");
        builder.addField("指令：%user <@user>", "查詢帳號資料", false);
        builder.addField("指令：%1A2B play", "猜數字遊戲\n(體驗不佳，第一次會出現沒有答案的BUG)", false);
        builder.addField("文字：機率", "訊息內有\"機率\"就會回傳一個機率值", false);
        builder.addField("文字：是否", "訊息內有\"是否\"就會回傳是或否", false);

        channel.sendMessageEmbeds(builder.build()).queue();

    }

    public void button(TextChannel channel) {
        Button primary = Button.primary("primary", "Primary");
        Button success = Button.success("success", "Success");
        Button secondary = Button.secondary("secondary", "Secondary");
        Button danger = Button.danger("danger", "Danger");
        Button link = Button.link("https://www.google.com.tw/?hl=zh_TW", "Link");
        channel.sendMessage("按鈕列表").setActionRow(primary, success, secondary, danger, link).queue();
    }
}
