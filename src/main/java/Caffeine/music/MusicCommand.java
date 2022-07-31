package Caffeine.music;

import Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicCommand {
    public void play(MessageReceivedEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = event.getChannel().asTextChannel();
        Message message = event.getMessage();
        User author = event.getAuthor();
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

        String[] splits = message.getContentRaw().split("play ");

        // Error: Not enough argument
        if (splits.length < 2) {
            EmbedUtil.sendErrorUsageEmbed(channel, author, "play <搜尋/連結>", "參數不足");
            return;
        }

        String trackURL = splits[1];

        // Check: Not a URL
        if (!trackURL.startsWith("http")) {
            trackURL = "ytsearch: " + trackURL + " audio";
        }

        PlayerManager.getINSTANCE().loadAndPlay(guild, channel, trackURL);
    }

    public void skip(Guild guild, TextChannel channel) {
        PlayerManager.getINSTANCE().nextTrack(guild, channel);
    }

    public void queue(Guild guild, TextChannel channel) {
        PlayerManager.getINSTANCE().getQueue(guild, channel);
    }

    public void leave(Guild guild) {
        guild.getAudioManager().closeAudioConnection();
    }

}
