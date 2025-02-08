package Caffeine.core;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ButtonHandler {

    public void handle(ButtonInteractionEvent event) {
        
        String id = event.getComponentId();

        if (id.equals("Primary_id")) {
            event.editMessage("Primary").queue();
            return;
        }
        
        if (id.equals("Success_id")) {
            event.editMessage("Success").queue();
        }
    }
}
