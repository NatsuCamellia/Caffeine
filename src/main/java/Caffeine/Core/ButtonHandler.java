package Caffeine.core;

import Caffeine.listener.GamblingListener;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ButtonHandler {

    GamblingListener gambling = MessageHandler.gambling;

    public void handle(ButtonInteractionEvent event) {
        
        String id = event.getComponentId();

        if (id.equals("primary")) {
            event.editMessage("Primary").queue();
            return;
        }
        
        if (id.equals("success")) {
            event.editMessage("Success").queue();
            return;
        }

        if (id.startsWith("coinflip")) {
            gambling.handleButton(event);
        }
    }
}
