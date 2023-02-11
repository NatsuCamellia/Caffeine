package Caffeine.core;

import Caffeine.inquiry.InquiryCommand;
import Caffeine.listener.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class SlashCommandHandler {

    InquiryCommand inquiry = new InquiryCommand();
    Command command = new Command();

    public void handle(SlashCommandInteractionEvent event) {

        switch (event.getName()) {
            case "user" -> inquiry.user(event);
            case "guild" -> inquiry.guild(event);
            case "delete" -> command.clear(event);
            case "probability" -> command.probability(event);
            case "trueorfalse" -> command.trueOrFalse(event);
        }
    }

    public static void initCommands(Guild guild) {
        try {
            guild.updateCommands().addCommands(
                    Commands.slash("user", "查詢使用者資料")
                            .addOption(OptionType.MENTIONABLE, "member", "成員", true),

                    Commands.slash("guild", "查詢伺服器資料"),

                    Commands.slash("delete", "清除多條訊息")
                            .addOption(OptionType.INTEGER, "rows", "訊息清除的數量", true)
                            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)),

                    Commands.slash("probability", "占卜一件事的機率")
                            .addOption(OptionType.STRING, "option", "欲占卜之事", true),

                    Commands.slash("trueorfalse", "占卜一件事的是否為真")
                            .addOption(OptionType.STRING, "option", "欲占卜之事", true)
            ).complete();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("【Warning】【" + guild.getName() + "】沒有授予 applications.commands");
        }
    }
}
