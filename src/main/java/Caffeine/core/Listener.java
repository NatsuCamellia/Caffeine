package Caffeine.core;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Listener extends ListenerAdapter {

    ButtonHandler buttonHandler = new ButtonHandler();
    SlashCommandHandler slashHandler = new SlashCommandHandler();

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        buttonHandler.handle(event);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        slashHandler.handle(event);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        event.getJDA().getGuilds().forEach(SlashCommandHandler::initCommands);
    }
}
