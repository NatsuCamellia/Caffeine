package net.natsucamellia.Caffeine.listener;

import net.natsucamellia.Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Command {

    public void clear (SlashCommandInteractionEvent event) {
        TextChannel channel = event.getChannel().asTextChannel();
        int rows = event.getOption("rows").getAsInt();

        if (rows < 2) {
            event.reply("請輸入大於 1 的數字").queue(m -> m.deleteOriginal().queueAfter(3, TimeUnit.SECONDS));
            return;
        }

        MessageHistory history = new MessageHistory(channel);
        channel.deleteMessages(history.retrievePast(rows).complete()).queue();

        event.reply("Done").queue(m -> m.deleteOriginal().queueAfter(3, TimeUnit.SECONDS));
    }

    public void probability(SlashCommandInteractionEvent event) {
        int probability = new Random().nextInt(101);
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(EmbedUtil.BLUE)
                .setAuthor("爪哇咖啡占卜奧術")
                .addField("問卜者", event.getUser().getAsTag(), true)
                .addField("問卜之事", Objects.requireNonNull(event.getOption("option")).getAsString(), true)
                .addField("結果", "根據咖啡因與水分子之間的量子作用力交互作用之下，我得到了量子力學中所定義的機率，而這件事的機率為：**" + probability + "%**", false);
        event.replyEmbeds(builder.build()).queue();
    }

    public void trueOrFalse(SlashCommandInteractionEvent event) {
        String tf = new Random().nextInt(2) == 1 ? "是" : "否"; // 0 ~ 1
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(EmbedUtil.BLUE)
                .setAuthor("爪哇咖啡占卜奧術")
                .addField("問卜者", event.getUser().getAsTag(), true)
                .addField("問卜之事", Objects.requireNonNull(event.getOption("option")).getAsString(), true)
                .addField("結果", "根據電晶體True與False之間的高速來回變換，我得出了答案為："+ tf, false);
        event.replyEmbeds(builder.build()).queue();
    }
}
