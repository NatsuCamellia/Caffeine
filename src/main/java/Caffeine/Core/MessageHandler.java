package Caffeine.core;

import Caffeine.listener.Command;
import Caffeine.listener.Economy;
import Caffeine.listener.GamblingListener;
import Caffeine.listener.Text;
import Caffeine.listener.Xp;
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
    Xp xp = new Xp();
    static GamblingListener gambling = new GamblingListener();

    public void handle(MessageReceivedEvent event) {
        
        if (event.getAuthor().isBot() | !event.isFromGuild()) return;

        printLog(event);
        
        Guild guild = event.getGuild();
        TextChannel channel = event.getTextChannel();
        User author = event.getAuthor();
        Message message = event.getMessage();

        xp.add_xp(author.getId(), guild.getId());
        
        if (isCommand(message)) {
            
            String command = message.getContentRaw().split(" ")[0].toLowerCase();
            
            if (command.equals(Bot.prefix + "test")) {
                System.out.println("test");
                return;
            }
            
            if (command.equals(Bot.prefix + "user")) {
                commandExecutor.user(guild, channel, author, message);
                return;
            }
            
            if (command.equals(Bot.prefix + "help")) {
                commandExecutor.help(channel, author);
                return;
            }

            if (command.equals(Bot.prefix + "button")) {
                commandExecutor.button(channel);
                return;
            }
            
            if (command.equals(Bot.prefix + "coinflip")) {
                gambling.coinflip(channel, author, message);
                return;
            }
            
            if (command.equals(Bot.prefix + "daily")) {
                economy.daily(channel, author);
                return;
            }
            
        } else {
            
            if (message.getContentRaw().contains("機率")) {
                text.chance(channel, author, message);
                return;
            }
            
            if (message.getContentRaw().contains("是否")) {
                text.yes_no(channel, author, message);
                return;
            }
        }
    }

    public boolean isCommand(Message message) {
        return message.getContentRaw().charAt(0) == '%';
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
