import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Command extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");
        String command = args[0];

        if (command.equalsIgnoreCase("user")) {

            try {
                Member member = event.getMessage().getMentionedMembers().get(0);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

                String account = member.getUser().getAsTag();
                String nickname = member.getEffectiveName();
                String avatar = member.getUser().getAvatarUrl() == null ? member.getUser().getDefaultAvatarUrl() : member.getUser().getAvatarUrl();
                String created = OffsetDateTime.ofInstant(member.getUser().getTimeCreated().toInstant(), ZoneId.systemDefault()).format(formatter) + "\n(UTC+8)";
                String joined = OffsetDateTime.ofInstant(member.getTimeJoined().toInstant(), ZoneId.systemDefault()).format(formatter) + "\n(UTC+8)";
                Iterator<Role> roleIterator = member.getRoles().iterator();
                String role = "";
                while (roleIterator.hasNext()) {
                    role += roleIterator.next().getAsMention();
                }
                User author = event.getAuthor();
                String authorAvatar = author.getAvatarUrl() == null ? author.getDefaultAvatarUrl() : author.getAvatarUrl();

                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(0x5398ed);
                builder.setAuthor(account);
                builder.setThumbnail(avatar);
                builder.setFooter("查詢者：" + author.getAsTag(), authorAvatar);
                builder.addField("帳戶名稱", account, true);
                builder.addField("伺服器暱稱", nickname, true);
                builder.addField("帳號創建時間", created, true);
                builder.addField("加入伺服器時間", joined, true);
                builder.addField("身分組", role, true);

                event.getChannel().sendMessageEmbeds(builder.build()).queue();

            } catch (IndexOutOfBoundsException e) {

                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0x5398ed);
                usage.setTitle("查詢帳號資料");
                usage.setDescription("用法：`user @mention`");

                event.getChannel().sendMessageEmbeds(usage.build()).queue();

            }

        }
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        if (event.getName().equals("ping")) {

            long time = System.currentTimeMillis();

            event.reply("Pong!").setEphemeral(true)
                    .flatMap(v -> event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time))
                    .queue();

        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
        } else {
            System.out.printf("[%s] [%s] %s: %s\n", event.getGuild().getName(), event.getTextChannel().getName(),
                    event.getMember().getEffectiveName(), event.getMessage().getContentDisplay());
        }
    }

}
