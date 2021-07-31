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
        String mention = event.getAuthor().getAsMention();
        User author = event.getAuthor();
        String id = event.getAuthor().getId();

        if (command.equalsIgnoreCase("getMention")) {

            event.getChannel().sendMessage(mention).queue();

        } else if (command.equalsIgnoreCase("getId")) {

            sendPrivateMessage(author, id);

        } else if (command.equalsIgnoreCase("ping")) {

            long time = System.currentTimeMillis();
            
            event.getChannel().sendMessage("Pinging...").queue(responce -> {responce.editMessageFormat("%d ms", System.currentTimeMillis() - time).queue();});
        }

    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        if (event.getName().equals("ping")) {

            long time = System.currentTimeMillis();

            event.reply("Pong!").setEphemeral(true) // reply or acknowledge
             .flatMap(v ->
                 event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
             ).queue(); // Queue both reply and edit

        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
        } else {
            System.out.printf("[%s] [%s] %s: %s\n",
                            event.getGuild().getName(),
                            event.getTextChannel().getName(),
                            event.getMember().getEffectiveName(),
                            event.getMessage().getContentDisplay());
        }
    }

    public void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue(channel -> {
            channel.sendMessage(content).queue();
        });
    }

}
