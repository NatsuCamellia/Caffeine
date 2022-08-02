package Caffeine.listener;

import Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.concurrent.TimeUnit;

public class Command {

    public void help(TextChannel channel, User author) {
        
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setFooter(author.getAsTag(), author.getAvatarUrl());
        builder.setAuthor("Caffeine 指令清單")
                .addField("指令：user <@user>", "查詢帳號資料", false)
                .addField("指令：guild", "查詢伺服器資料", false)
                .addField("指令：play <網址>", "播放網址所連結的音樂", false)
                .addField("指令：skip", "跳過目前播放的音樂", false)
                .addField("指令：queue", "查看待播清單", false)
                .addField("指令：help", "顯示這個指令清單", false)
                .addField("文字：機率", "訊息內有\"機率\"就會回傳一個機率值", false)
                .addField("文字：是否", "訊息內有\"是否\"就會回傳是或否", false);

        channel.sendMessageEmbeds(builder.build()).queue();

    }

    public void button(TextChannel channel) {
        Button primary = Button.primary("primary", "Primary");
        Button success = Button.success("success", "Success");
        Button secondary = Button.secondary("secondary", "Secondary");
        Button danger = Button.danger("danger", "Danger");
        Button link = Button.link("https://www.google.com.tw/?hl=zh_TW", "Link");
        channel.sendMessage("按鈕列表").setActionRow(primary, success, secondary, danger, link).queue();
    }

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
