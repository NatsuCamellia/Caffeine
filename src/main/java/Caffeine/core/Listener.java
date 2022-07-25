package Caffeine.core;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {

    MessageHandler messageHandler = new MessageHandler();
    ButtonHandler buttonHandler = new ButtonHandler();
    
    public void onMessageReceived(MessageReceivedEvent event) {     
        messageHandler.handle(event);       
    }
    
    public void onButtonInteraction(ButtonInteractionEvent event) {
        buttonHandler.handle(event);
    }
}
