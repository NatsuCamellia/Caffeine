package Caffeine.riotapi;

import Caffeine.riotapi.dto.ChampionMasteryDTO;
import Caffeine.riotapi.dto.LeagueEntryDTO;
import Caffeine.riotapi.dto.SummonerDTO;
import Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RiotCommand {
    private RiotDataFetcher fetcher = new RiotDataFetcher();

    public RiotCommand(String API_KEY) {
        fetcher.setAPI_KEY(API_KEY);
    }

    // Get summoner info, including name, rank and top mastery
    public void getSummonerInfo(SlashCommandInteractionEvent event) {
        String summonerName = event.getOption("summoner_name").getAsString();
        SummonerDTO summoner = fetcher.getSummonerByName(summonerName);

        if (summoner == null) {
            EmbedBuilder builder = summonerDNE(summonerName);
            event.replyEmbeds(builder.build()).queue();
            return;
        }

        Set<LeagueEntryDTO> leagueSet = fetcher.getLeagueBySummonerId(summoner.getId());
        List<ChampionMasteryDTO> mastery = fetcher.getMasteryTopBySummonerId(summoner.getId());


        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(summonerName).setColor(EmbedUtil.BLUE);


        builder.addField("召喚師等級", String.valueOf(summoner.getSummonerLevel()), true);

        for (LeagueEntryDTO league : leagueSet) {
            builder.addField(league.getQueueType(), league.getTier() + " " + league.getRank(), true);
        }

        builder.addField("專精英雄（按專精分數）", getMasteryString(mastery, 1), false);

        event.replyEmbeds(builder.build()).queue();
    }

    // Get the first page of all the mastery
    public void getMastery(SlashCommandInteractionEvent event) {
        String summonerName = event.getOption("summoner_name").getAsString();
        SummonerDTO summoner = fetcher.getSummonerByName(summonerName);
        if (summoner == null) {
            event.replyEmbeds(summonerDNE(summonerName).build()).queue();
            return;
        }

        List<ChampionMasteryDTO> mastery = fetcher.getMasteryBySummonerId(summoner.getId());

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(summonerName);
        builder.setColor(EmbedUtil.BLUE);

        int page = 1;
        builder.addField("專精英雄（按專精分數）", getMasteryString(mastery, page), false);
        builder.setFooter(String.format("Page %d/%d", page, getMaxMasteryPage(mastery)));
        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.primary("riot.mastery:first_page", Emoji.fromUnicode("⏪")));
        buttons.add(Button.primary("riot.mastery:prev_page", Emoji.fromUnicode("◀")));
        buttons.add(Button.primary("riot.mastery:next_page", Emoji.fromUnicode("▶")));
        buttons.add(Button.primary("riot.mastery:last_page", Emoji.fromUnicode("⏩")));
        event.replyEmbeds(builder.build()).addActionRow(buttons).queue();
    }

    // Convert list of mastery to a formatted string
    private String getMasteryString(List<ChampionMasteryDTO> mastery, int page) {
        page = Math.max(1, page);
        page = Math.min(page, getMaxMasteryPage(mastery));
        mastery = mastery.subList((page - 1) * 10, Math.min(page * 10, mastery.size()));

        StringBuilder masteryString = new StringBuilder();
        for (ChampionMasteryDTO champion : mastery) {
            String championName = fetcher.getChampionNameById(champion.getChampionId());
            int level = champion.getChampionLevel();
            int point = champion.getChampionPoints();
            masteryString.append(String.format("專精 %d - `%7d` 專精分｜%s\n",
                    level, point, championName));
        }
        return masteryString.toString();
    }

    public void EditMasteryEmbed(ButtonInteractionEvent event) {
        MessageEmbed embed = event.getMessage().getEmbeds().get(0);
        String summonerName = embed.getTitle();
        SummonerDTO summoner = fetcher.getSummonerByName(summonerName);
        List<ChampionMasteryDTO> mastery = fetcher.getMasteryBySummonerId(summoner.getId());

        int page = Integer.parseInt(embed.getFooter().getText().split(" ")[1].split("/")[0]);
        int lastPage = getMaxMasteryPage(mastery);

        String action = event.getComponentId().split(":")[1];
        switch (action) {
            case "first_page" -> page = 1;
            case "prev_page" -> page = Math.max(1, page - 1);
            case "next_page" -> page = Math.min(page + 1, lastPage);
            case "last_page" -> page = lastPage;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(embed.getTitle());
        builder.setColor(EmbedUtil.BLUE);
        builder.setFooter(String.format("Page %d/%d", page, lastPage));
        builder.addField("專精英雄（按專精分數）", getMasteryString(mastery, page), false);
        event.editMessageEmbeds(builder.build()).queue();
    }

    private int getMaxMasteryPage(List<ChampionMasteryDTO> mastery) {
        return (int)Math.ceil((double)mastery.size() / 10);
    }

    public EmbedBuilder summonerDNE(String summonerName) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(summonerName).setColor(EmbedUtil.RED);

        builder.setDescription(String.format("召喚師**%s**不存在。", summonerName));
        return builder;
    }
}
