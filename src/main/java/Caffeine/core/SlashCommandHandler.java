package Caffeine.core;

import Caffeine.inquiry.InquiryCommand;
import Caffeine.listener.Command;
import Caffeine.music.MusicCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class SlashCommandHandler {

    InquiryCommand inquiry = new InquiryCommand();
    MusicCommand music = new MusicCommand();
    Command command = new Command();

    public void handle(SlashCommandInteractionEvent event) {

        switch (event.getName()) {
            case "user" -> inquiry.user(event);
            case "guild" -> inquiry.guild(event);
            case "play" -> music.play(event);
            case "skip" -> music.skip(event);
            case "queue" -> music.queue(event);
            case "leave" -> music.leave(event);
            case "clear" -> music.clear(event);
            case "delete" -> command.clear(event);
        }
    }

    public static void initCommands(Guild guild) {
        try {
            guild.updateCommands().addCommands(
                    Commands.slash("user", "查詢使用者資料")
                            .addOption(OptionType.MENTIONABLE, "member", "成員", true),
                    Commands.slash("guild", "查詢伺服器資料"),
                    Commands.slash("play", "播放音樂")
                            .addOption(OptionType.STRING, "search", "搜尋或音樂網址", true),
                    Commands.slash("skip", "跳過曲目"),
                    Commands.slash("queue", "查詢播放清單"),
                    Commands.slash("leave", "使機器人離開語音頻道"),
                    Commands.slash("delete", "清除多條訊息，伺服器擁有者專用")
                            .addOption(OptionType.INTEGER, "rows", "訊息清除的數量", true),
                    Commands.slash("clear", "清空播放清單")
            ).complete();
        } catch (Exception e) {
            System.out.println("【Warning】【" + guild.getName() + "】沒有授予 applications.commands");
        }
    }
}
