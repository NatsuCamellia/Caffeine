package Caffeine.core;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ButtonHandler {

    public void handle(ButtonInteractionEvent event) {
        
        String id = event.getComponentId();

        if (id.equals("primary")) {
            event.editMessage("Primary").queue();
            return;
        }
        
        if (id.equals("success")) {
            event.editMessage("Success").queue();
        }
    }
}
