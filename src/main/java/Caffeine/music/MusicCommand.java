package Caffeine.music;

import Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicCommand {
    public void play(SlashCommandInteractionEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = event.getChannel().asTextChannel();
        User author = event.getUser();
        String trackURL = event.getOption("search").getAsString();
        Member member = event.getMember();
        Member jdaMember = guild.getSelfMember();

        // Error: Member not in a voice channel
        if (!member.getVoiceState().inAudioChannel()) {
            EmbedUtil.sendErrorUsageEmbed(channel, author, "play <搜尋/連結>", "你必須在語音頻道內使用這個指令");
            return;
        }

        VoiceChannel memberChannel = (VoiceChannel) member.getVoiceState().getChannel();

        // Check: Bot not in a voice channel
        if (!jdaMember.getVoiceState().inAudioChannel()) {
            final AudioManager audioManager = guild.getAudioManager();
            audioManager.openAudioConnection(memberChannel);
        }

        VoiceChannel botChannel = (VoiceChannel) jdaMember.getVoiceState().getChannel();

        // Error: Member and bot not in the same voice channel
        if (jdaMember.getVoiceState().inAudioChannel() && botChannel != memberChannel) {
            EmbedUtil.sendErrorUsageEmbed(channel, author, "play <搜尋/連結>", "你必須與機器人在同一個語音頻道內");
            return;
        }

        // Check: Not a URL
        if (!trackURL.startsWith("http")) {
            trackURL = "ytsearch: " + trackURL + " audio";
        }

        PlayerManager.getINSTANCE().loadAndPlay(event, guild, channel, trackURL);
    }

    public void skip(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        TextChannel channel = event.getChannel().asTextChannel();
        PlayerManager.getINSTANCE().nextTrack(event, guild, channel);
    }

    public void queue(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        TextChannel channel = event.getChannel().asTextChannel();
        PlayerManager.getINSTANCE().getQueue(event, guild, channel);
    }

    public void leave(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        PlayerManager.getINSTANCE().leave(event, guild);
    }

}
