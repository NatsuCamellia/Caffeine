package Caffeine;
import java.util.HashMap;

import Caffeine.Gambling.CoinFlip;
import Caffeine.Gambling.Gambling;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GamblingListener extends ListenerAdapter {
    
    HashMap<User, Gambling> game = new HashMap<User, Gambling>();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");
        String command = args[0];
        User author = event.getAuthor();
        
        if (command.equalsIgnoreCase(Bot.prefix + "coinflip")) {
            
            game.put(author, new CoinFlip(event));

        }
    }

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        String id = event.getComponentId();
        if (id.startsWith("coinflip")) {
            CoinFlip coinFlip = (CoinFlip)game.get(event.getUser());
            coinFlip.handleClick(event);
        }
    }
}
