package Caffeine.listener;
import java.util.HashMap;

import Caffeine.gambling.CoinFlip;
import Caffeine.gambling.Gambling;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class GamblingListener {
    
    HashMap<User, Gambling> game = new HashMap<User, Gambling>();

    public void coinflip(TextChannel channel, User user, Message message) {
        game.put(user, new CoinFlip(channel, user, message));
    }

    public void handleButton(ButtonInteractionEvent event) {
        CoinFlip coinFlip = (CoinFlip)game.get(event.getUser());
        coinFlip.handleClick(event);
    }
}
