package Caffeine.listener;

import Caffeine.gambling.CoinFlip;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class GamblingListener {

    public void coinflip(TextChannel channel, User user, Message message) {
        CoinFlip.map.put(user, new CoinFlip(channel, user, message));
    }

    public void handleButton(ButtonInteractionEvent event) {
        try {
            CoinFlip coinFlip = (CoinFlip)CoinFlip.map.get(event.getUser());
            coinFlip.handleClick(event);
        } catch (NullPointerException e) {
            // User is not in game
        }
    }
}
