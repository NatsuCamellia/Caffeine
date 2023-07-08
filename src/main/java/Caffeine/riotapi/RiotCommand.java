package Caffeine.riotapi;

import Caffeine.riotapi.dto.ChampionMasteryDTO;
import Caffeine.riotapi.dto.LeagueEntryDTO;
import Caffeine.riotapi.dto.SummonerDTO;
import Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;
import java.util.Set;

public class RiotCommand {
    private RiotDataFetcher fetcher = new RiotDataFetcher();

    public RiotCommand(String API_KEY) {
        fetcher.setAPI_KEY(API_KEY);
    }

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

        StringBuilder masteryString = new StringBuilder();
        for (ChampionMasteryDTO champion : mastery) {
            String championName = fetcher.getChampionNameById(champion.getChampionId());
            int level = champion.getChampionLevel();
            int point = champion.getChampionPoints();
            boolean chest = champion.isChestGranted();
            masteryString.append(String.format("%s：專精 %d - %d 專精分｜寶箱：%s\n",
                    championName, level, point, chest ? ":white_check_mark:" : ":negative_squared_cross_mark:"));
        }
        builder.addField("專精英雄（按專精分數）", masteryString.toString(), false);

        event.replyEmbeds(builder.build()).queue();
    }

    public EmbedBuilder summonerDNE(String summonerName) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(summonerName).setColor(EmbedUtil.RED);

        builder.setDescription(String.format("召喚師**%s**不存在。", summonerName));
        return builder;
    }
}
