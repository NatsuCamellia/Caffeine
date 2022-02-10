package Caffeine.listener;
import java.util.HashMap;

import Caffeine.core.Bot;
import Caffeine.gambling.CoinFlip;
import Caffeine.gambling.Gambling;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GamblingListener extends ListenerAdapter {
    
    
    HashMap<User, Gambling> game = new HashMap<User, Gambling>();

    public void onMessageReceived(MessageReceivedEvent event) {
        
        if (!event.isFromGuild()) return;

        String[] args = event.getMessage().getContentRaw().split(" ");
        String command = args[0];
        User author = event.getAuthor();
        
        if (command.equalsIgnoreCase(Bot.prefix + "coinflip")) {
            
            game.put(author, new CoinFlip(event));

        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String id = event.getComponentId();
        if (id.startsWith("coinflip")) {
            CoinFlip coinFlip = (CoinFlip)game.get(event.getUser());
            coinFlip.handleClick(event);
        }
    }
}
