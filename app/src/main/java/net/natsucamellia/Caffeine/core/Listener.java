package net.natsucamellia.Caffeine.core;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Listener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger("Caffeine");
    private final ButtonHandler buttonHandler = new ButtonHandler();
    private final SlashCommandHandler slashHandler = new SlashCommandHandler();

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
        event.getJDA().getGuilds().forEach(guild -> {
            logger.info("Initializing server {}", guild.getName());
            SlashCommandHandler.initCommands(guild);
        });
    }
}
