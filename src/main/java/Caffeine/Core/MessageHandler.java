package Caffeine.core;

import Caffeine.listener.Command;
import Caffeine.listener.Economy;
import Caffeine.listener.GamblingListener;
import Caffeine.listener.Text;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageHandler {

    Command commandExecutor = new Command();
    Economy economy = new Economy();
    Text text = new Text();
    static GamblingListener gambling = new GamblingListener();

    public void handle(MessageReceivedEvent event) {
        
        if (event.getAuthor().isBot()) return;

        printLog(event);

        if (!event.isFromGuild()) return; // Not support PM currently
        
        Guild guild = event.getGuild();
        TextChannel channel = event.getTextChannel();
        User author = event.getAuthor();
        Message message = event.getMessage();

        String[] args = message.getContentRaw().split(" ");
        String command = args[0];

        if (command.equalsIgnoreCase(Bot.prefix + "test")) {
            System.out.println("test");
            return;
        }

        if (command.equalsIgnoreCase(Bot.prefix + "user")) {
            commandExecutor.user(guild, channel, author, message);
            return;
        }

        if (command.equalsIgnoreCase(Bot.prefix + "help")) {
            commandExecutor.help(channel, author);
            return;
        }

        if (command.equalsIgnoreCase(Bot.prefix + "button")) {
            commandExecutor.button(channel);
            return;
        }

        if (command.equalsIgnoreCase(Bot.prefix + "coinflip")) {
            gambling.coinflip(channel, author, message);
            return;
        }

        if (command.equalsIgnoreCase(Bot.prefix + "daily")) {
            economy.daily(channel, author);
            return;
        }

        if (message.getContentRaw().contains("機率")) {
            text.chance(channel, author, message);
            return;
        }

        if (message.getContentRaw().contains("是否")) {
            text.yes_no(channel, author, message);
            return;
        }
    }

    public void printLog(MessageReceivedEvent event) {

        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
        } else {
            System.out.printf("[%s] [%s] %s: %s\n", event.getGuild().getName(), event.getTextChannel().getName(),
                    event.getMember().getEffectiveName(), event.getMessage().getContentDisplay());
        }
    }
}
