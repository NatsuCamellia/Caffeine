package Caffeine.Listener;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import Caffeine.Core.Bot;
import Caffeine.Util.EmbedUtil;
import Caffeine.Util.JsonUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class Command extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
        } else {
            System.out.printf("[%s] [%s] %s: %s\n", event.getGuild().getName(), event.getTextChannel().getName(),
                    event.getMember().getEffectiveName(), event.getMessage().getContentDisplay());
        }

        if (!event.isFromGuild()) return;
        String[] args = event.getMessage().getContentRaw().split(" ");
        String command = args[0];
        TextChannel channel = event.getTextChannel();
        User author = event.getAuthor();

        if (command.equalsIgnoreCase(Bot.prefix + "user")) {

            Member member;
            try {
                member = event.getMessage().getMentionedMembers().get(0);
            } catch (IndexOutOfBoundsException e) {
                member = event.getMember();
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

            String account = member.getUser().getAsTag();
            String nickname = member.getEffectiveName();
            String avatar = member.getUser().getAvatarUrl() == null ? member.getUser().getDefaultAvatarUrl() : member.getUser().getAvatarUrl();
            String created = OffsetDateTime.ofInstant(member.getUser().getTimeCreated().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
            String joined = OffsetDateTime.ofInstant(member.getTimeJoined().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
            JsonUtil json = new JsonUtil(member.getId());
            Long overall_xp = json.getAccountXp();
            Long guild_xp = json.getGuildXp(event.getGuild().getId());
            Long balance = json.getBalance();
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
            builder.addField("帳號經驗", String.valueOf(overall_xp), true);
            builder.addField("帳號在本伺服器經驗", guild_xp.toString(), true);
            builder.addField("金錢", String.valueOf(balance), true);
            builder.addField("身分組", role, true);

            event.getChannel().sendMessageEmbeds(builder.build()).queue();
        }
    
        if (command.equalsIgnoreCase(Bot.prefix + "help")) {
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

        if (command.equalsIgnoreCase(Bot.prefix + "button")) {
            Button primary = Button.primary("primary", "Primary");
            Button success = Button.success("success", "Success");
            Button secondary = Button.secondary("secondary", "Secondary");
            Button danger = Button.danger("danger", "Danger");
            Button link = Button.link("https://www.google.com.tw/?hl=zh_TW", "Link");
            event.getChannel().sendMessage("按鈕列表").setActionRow(primary, success, secondary, danger, link).queue();
        }

    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String id = event.getComponentId();
        if (id.equals("primary")) {
            event.editMessage("Primary").queue();
        } else if (id.equals("success")) {
            event.editMessage("Success").queue();
        }
    }
}
