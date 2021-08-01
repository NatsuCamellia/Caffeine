import java.time.format.DateTimeFormatter;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Command extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] message = event.getMessage().getContentRaw().split(" ");
        String command = message[0];

        if (command.equalsIgnoreCase("getMention")) {

            String mention = event.getAuthor().getAsMention();

            event.getChannel().sendMessage(mention).queue();

        } else if (command.equalsIgnoreCase("getId")) {

            sendPrivateMessage(event.getAuthor(), event.getAuthor().getId());

        } else if (command.equalsIgnoreCase("info")) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

            String created = event.getAuthor().getTimeCreated().format(formatter);

            event.getChannel().sendMessage(created).queue();

        } else if (command.equalsIgnoreCase("join")) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

            String joined = event.getMember().getTimeJoined().format(formatter);

            event.getChannel().sendMessage(joined).queue();
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

    public void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue(channel -> {
            channel.sendMessage(content).queue();
        });
    }

}
