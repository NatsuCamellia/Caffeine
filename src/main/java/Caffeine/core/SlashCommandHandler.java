package Caffeine.core;

import Caffeine.inquiry.InquiryCommand;
import Caffeine.listener.Command;
import Caffeine.music.MusicCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class SlashCommandHandler {

    InquiryCommand inquiry = new InquiryCommand();
    MusicCommand music = new MusicCommand();
    Command command = new Command();

    public void handle(SlashCommandInteractionEvent event) {
        User author = event.getUser();
        Guild guild = event.getGuild();

        switch (event.getName()) {
            case "user" -> event.replyEmbeds(inquiry.user(author, event.getOption("member").getAsMember())).queue();
            case "guild" -> event.replyEmbeds(inquiry.guild(guild, author)).queue();
            case "play" -> music.play(event);
            case "skip" -> music.skip(event);
            case "queue" -> music.queue(event);
            case "leave" -> music.leave(guild);
            case "clear" -> command.clear(event);
        }
    }

    public static void initCommands(Guild guild) {
        try {
            guild.updateCommands().addCommands(
                    Commands.slash("user", "Inquiry user")
                            .addOption(OptionType.MENTIONABLE, "member", "member", true),
                    Commands.slash("guild", "Inquiry guild"),
                    Commands.slash("play", "Play Music")
                            .addOption(OptionType.STRING, "search", "search", true),
                    Commands.slash("skip", "Skip music"),
                    Commands.slash("queue", "Query the queue"),
                    Commands.slash("leave", "Disconnect the bot"),
                    Commands.slash("clear", "Clear the messages, OWNER ONLY")
                            .addOption(OptionType.INTEGER, "rows", "The number of rows to clear", true)
            ).complete();
        } catch (Exception e) {
            System.out.println("【Warning】【" + guild.getName() + "】沒有授予 applications.commands");
        }
    }
}
