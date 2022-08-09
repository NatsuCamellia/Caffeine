package Caffeine.core;

import Caffeine.listener.Text;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageHandler {

    Text text = new Text();

    public void handle(MessageReceivedEvent event) {
        
        if (event.getAuthor().isBot() | !event.isFromGuild()) return;

        printLog(event);
        
        TextChannel channel = event.getChannel().asTextChannel();
        User author = event.getAuthor();
        Message message = event.getMessage();

        if (message.getContentRaw().contains("機率")) {
            text.chance(channel, author, message);
            return;
        }

        if (message.getContentRaw().contains("是否")) {
            text.yes_no(channel, author, message);
        }
    }

    public void printLog(MessageReceivedEvent event) {

        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
        } else {
            System.out.printf("[%s] [%s] %s: %s\n", event.getGuild().getName(), event.getChannel().asTextChannel().getName(),
                    event.getMember().getEffectiveName(), event.getMessage().getContentDisplay());
        }
    }
}
