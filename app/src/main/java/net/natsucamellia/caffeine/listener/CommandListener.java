package net.natsucamellia.caffeine.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.natsucamellia.caffeine.service.guild.GuildInfo;
import net.natsucamellia.caffeine.service.guild.GuildService;
import net.natsucamellia.caffeine.service.member.MemberInfo;
import net.natsucamellia.caffeine.service.member.MemberService;
import net.natsucamellia.caffeine.util.EmbedUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Random;

public class CommandListener extends ListenerAdapter {

    private final Logger logger;

    private final MemberService memberService;
    private final GuildService guildService;

    public CommandListener(MemberService memberService, GuildService guildService) {
        this.logger = LoggerFactory.getLogger(CommandListener.class);
        this.memberService = memberService;
        this.guildService = guildService;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        logCommand(event);
        switch (event.getName()) {
            case "user" -> user(event);
            case "guild" -> guild(event);
            case "probability" -> probability(event);
            case "trueorfalse" -> trueOrFalse(event);
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            logger.info("Initializing slash commands for guild {}", guild.getName());
            try {
                guild.updateCommands().addCommands(
                        Commands.slash("user", "查詢使用者資料")
                                .addOption(OptionType.MENTIONABLE, "member", "成員", true),

                        Commands.slash("guild", "查詢伺服器資料"),

                        Commands.slash("probability", "占卜一件事的機率")
                                .addOption(OptionType.STRING, "option", "欲占卜之事", true),

                        Commands.slash("trueorfalse", "占卜一件事是否為真")
                                .addOption(OptionType.STRING, "option", "欲占卜之事", true)
                ).complete();
            } catch (Exception e) {
                logger.error("伺服器 {} 沒有授予 applications.commands", guild.getName(), e);
            }
        }
    }

    private void user(SlashCommandInteractionEvent event) {
        var option = event.getOption("member");
        if (option == null) {
            // TODO
            return;
        }

        Member member = option.getAsMember();
        if (member == null) {
            // TODO: reply error
            return;
        }

        MemberInfo info = memberService.getMemberInfo(member);
        if (info == null) {
            // TODO: reply error
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setTitle("資訊查詢");
        builder.setThumbnail(info.thumbnailUrl());
        builder.addField("帳號名稱", info.account(), true);
        builder.addField("伺服器暱稱", info.effectiveName(), true);
        builder.addField("帳號創建時間", info.createdTime(), true);
        builder.addField("加入伺服器時間", info.joinedTime(), true);

        StringBuilder role = new StringBuilder();
        info.roles().forEach(r -> role.append(r.getAsMention()));
        builder.addField("身分組", role.toString(), true);

        event.replyEmbeds(builder.build()).queue();
    }

    private void guild(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();

        if (guild == null) {
            // TODO: reply error
            return;
        }

        GuildInfo info = guildService.getGuildInfo(guild);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setTitle("資訊查詢");

        builder.addField("伺服器名稱", info.name(), true);
        builder.setThumbnail(info.iconUrl());
        builder.addField("創建時間", info.createdTime(), true);
        builder.addField("成員人數", info.memberCount() + "人", true);
        builder.addField("線上人數", info.onlineCount() + "人", true);

        event.replyEmbeds(builder.build()).queue();
    }

    private void probability(SlashCommandInteractionEvent event) {
        int probability = new Random().nextInt(101);
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(EmbedUtil.BLUE)
                .setAuthor("爪哇咖啡占卜奧術")
                .addField("問卜者", event.getUser().getAsTag(), true)
                .addField("問卜之事", Objects.requireNonNull(event.getOption("option")).getAsString(), true)
                .addField("結果", "根據咖啡因與水分子之間的量子作用力交互作用之下，我得到了量子力學中所定義的機率，而這件事的機率為：**" + probability + "%**", false);
        event.replyEmbeds(builder.build()).queue();
    }

    private void trueOrFalse(SlashCommandInteractionEvent event) {
        String tf = new Random().nextInt(2) == 1 ? "是" : "否"; // 0 ~ 1
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(EmbedUtil.BLUE)
                .setAuthor("爪哇咖啡占卜奧術")
                .addField("問卜者", event.getUser().getAsTag(), true)
                .addField("問卜之事", Objects.requireNonNull(event.getOption("option")).getAsString(), true)
                .addField("結果", "根據電晶體True與False之間的高速來回變換，我得出了答案為："+ tf, false);
        event.replyEmbeds(builder.build()).queue();
    }

    private void logCommand(SlashCommandInteractionEvent event) {
        // TODO: logging for slash commands
        if (event.isGuildCommand()) {
            Guild guild = event.getGuild();
            assert guild != null; // The command is a guild command
            var channel = event.getChannel();
            Member member = event.getMember();
            assert member != null; // The command is a guild command
            String command = event.getName();

            logger.info("[{}] [{}] {} used command: {}", guild.getName(), channel.getName(), member.getEffectiveName(), command);
        }
    }
}
