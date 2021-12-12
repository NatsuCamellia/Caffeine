import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Command extends ListenerAdapter {

    HashMap<User, GuessNumber> guessNumber = new HashMap<User, GuessNumber>();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");
        String command = args[0];
        TextChannel channel = event.getChannel();
        User author = event.getAuthor();

        if (command.equalsIgnoreCase(Bot.prefix + "user")) {

            try {
                Member member = event.getMessage().getMentionedMembers().get(0);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

                String account = member.getUser().getAsTag();
                String nickname = member.getEffectiveName();
                String avatar = member.getUser().getAvatarUrl() == null ? member.getUser().getDefaultAvatarUrl() : member.getUser().getAvatarUrl();
                String created = OffsetDateTime.ofInstant(member.getUser().getTimeCreated().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
                String joined = OffsetDateTime.ofInstant(member.getTimeJoined().toInstant(), ZoneId.of("UTC+8")).format(formatter) + "\n(UTC+8)";
                Iterator<Role> roleIterator = member.getRoles().iterator();
                String role = "";
                while (roleIterator.hasNext()) role += roleIterator.next().getAsMention();

                String authorAvatar = author.getAvatarUrl() == null ? author.getDefaultAvatarUrl() : author.getAvatarUrl();

                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(EmbedUtil.blue);
                builder.setAuthor(account);
                builder.setThumbnail(avatar);
                builder.setFooter("查詢者：" + author.getAsTag(), authorAvatar);
                builder.addField("帳戶名稱", account, true);
                builder.addField("伺服器暱稱", nickname, true);
                builder.addField("帳號創建時間", created, true);
                builder.addField("加入伺服器時間", joined, true);
                builder.addField("身分組", role, true);

                event.getChannel().sendMessageEmbeds(builder.build()).queue();

                return;

            } catch (IndexOutOfBoundsException e) {

                EmbedUtil.sendUsageEmbed(channel, "查詢帳號資料", "user @mention");
                return;

            }

        }

        if (command.equalsIgnoreCase(Bot.prefix + "1A2B")) {
            // 只有 1A2B
            if (args.length == 1) {

                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(EmbedUtil.blue);
                usage.setTitle(":1234:猜數字1A2B");
                usage.setDescription(String.format("用法：%n%14s `1A2B play`%n%14s `1A2B number`%n%14s `1A2B stop`",
                        "開始一把新遊戲", "猜數字", "翻桌放棄"));

                event.getChannel().sendMessageEmbeds(usage.build()).queue();
                return;
            }

            // 尚未創建物件
            if (!guessNumber.containsKey(event.getAuthor())) {
                guessNumber.put(author, new GuessNumber(author));
            }

            guessNumber.get(event.getAuthor()).run(channel, args[1]);
            return;
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
        } else {
            System.out.printf("[%s] [%s] %s: %s\n", event.getGuild().getName(), event.getTextChannel().getName(),
                    event.getMember().getEffectiveName(), event.getMessage().getContentDisplay());
        }
    }

}
