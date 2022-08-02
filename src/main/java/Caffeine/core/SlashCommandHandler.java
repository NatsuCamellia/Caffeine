package Caffeine.core;

import Caffeine.inquiry.InquiryCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class SlashCommandHandler {

    InquiryCommand inquiry = new InquiryCommand();

    public void handle(SlashCommandInteractionEvent event) {
        User author = event.getUser();
        Guild guild = event.getGuild();

        switch (event.getName()) {
            case "user" -> event.replyEmbeds(inquiry.user(author, event.getOption("member").getAsMember())).queue();
            case "guild" -> event.replyEmbeds(inquiry.guild(guild, author)).queue();
        }
    }

    public static void initCommands(Guild guild) {
        try {
            guild.updateCommands().addCommands(
                    Commands.slash("user", "Inquiry user")
                            .addOption(OptionType.MENTIONABLE, "member", "member", true),
                    Commands.slash("guild", "Inquiry guild")
            ).complete();
        } catch (Exception e) {
            System.out.println("【Warning】【" + guild.getName() + "】沒有授予 applications.commands");
        }
    }
}
