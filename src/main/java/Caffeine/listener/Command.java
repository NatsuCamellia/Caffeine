package Caffeine.listener;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.concurrent.TimeUnit;

public class Command {

    public void clear (SlashCommandInteractionEvent event) {
        TextChannel channel = event.getChannel().asTextChannel();
        Member member = event.getMember();
        int rows = event.getOption("rows").getAsInt();

        if (rows < 2) {
            event.reply("請輸入大於 1 的數字").queue(m -> m.deleteOriginal().queueAfter(3, TimeUnit.SECONDS));
            return;
        }

        if (member.isOwner()) {
            MessageHistory history = new MessageHistory(channel);
            channel.deleteMessages(history.retrievePast(rows).complete()).queue();
        }

        event.reply("Done").queue(m -> m.deleteOriginal().queueAfter(3, TimeUnit.SECONDS));
    }
}
