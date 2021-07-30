import net.dv8tion.jda.api.entities.User;
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
        }

    }

    public void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue(channel -> {
            channel.sendMessage(content).queue();
        });
    }

}
