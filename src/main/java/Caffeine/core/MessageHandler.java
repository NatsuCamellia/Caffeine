package Caffeine.core;

import Caffeine.listener.Command;
import Caffeine.listener.Text;
import Caffeine.music.MusicCommand;
import Caffeine.test.ManagerTest;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageHandler {

    Command commandExecutor = new Command();
    ManagerTest test = new ManagerTest();
    Text text = new Text();
    MusicCommand music = new MusicCommand();

    public void handle(MessageReceivedEvent event) {
        
        if (event.getAuthor().isBot() | !event.isFromGuild()) return;

        printLog(event);
        
        Guild guild = event.getGuild();
        TextChannel channel = event.getChannel().asTextChannel();
        User author = event.getAuthor();
        Message message = event.getMessage();
        
        if (isCommand(message)) {
            
            String command = message.getContentRaw().split(" ")[0].toLowerCase();
            
            if (command.equals(Bot.prefix + "test")) {
                test.handle(event);
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

            if (command.equals(Bot.prefix + "play")) {
                music.play(event);
                return;
            }

            if (command.equals(Bot.prefix + "skip")) {
                music.skip(guild);
                return;
            }

            if (command.equals(Bot.prefix + "leave")) {
                music.leave(guild);
            }
            
        } else {
            
            if (message.getContentRaw().contains("機率")) {
                text.chance(channel, author, message);
                return;
            }
            
            if (message.getContentRaw().contains("是否")) {
                text.yes_no(channel, author, message);
            }
        }
    }

    public boolean isCommand(Message message) {
        return message.getContentRaw().startsWith(Bot.prefix);
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
