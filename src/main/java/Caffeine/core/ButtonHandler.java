package Caffeine.core;

import Caffeine.riotapi.RiotCommand;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ButtonHandler {

    RiotCommand riotCommand = new RiotCommand(System.getenv("RIOT_API_KEY"));

    public void handle(ButtonInteractionEvent event) {
        
        String id = event.getComponentId();

        if (id.equals("Primary_id")) {
            event.editMessage("Primary").queue();
            return;
        }
        
        if (id.equals("Success_id")) {
            event.editMessage("Success").queue();
        }

        if (id.startsWith("riot.mastery")) {
            riotCommand.EditMasteryEmbed(event);
        }
    }
}
