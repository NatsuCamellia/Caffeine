package Caffeine.music;

import Caffeine.core.Bot;
import Caffeine.util.EmbedUtil;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicCommand {
    public void play(MessageReceivedEvent event) {

        TextChannel channel = event.getChannel().asTextChannel();
        Guild guild = event.getGuild();
        Message message = event.getMessage();
        User author = event.getAuthor();

        if (!event.getMember().getVoiceState().inAudioChannel()) {
            EmbedUtil.sendMessageEmbed(channel, "音樂指令", "你必須要在語音頻道內使用這個指令", author);
            return;
        }

        if (!guild.getMemberById(event.getJDA().getSelfUser().getId()).getVoiceState().inAudioChannel()) {
            final AudioManager audioManager = event.getGuild().getAudioManager();
            final VoiceChannel memberChannel = (VoiceChannel) event.getMember().getVoiceState().getChannel();

            audioManager.openAudioConnection(memberChannel);
        }

        String link = message.getContentRaw().split(Bot.prefix + "play")[1].substring(1);

        if (!isUrl(link)) {
            link = "ytsearch: " + link + " audio";
        }

        PlayerManager.getINSTANCE().loadAndPlay(channel, link);
    }

    public void skip(Guild guild) {
        PlayerManager.getINSTANCE().nextTrack(guild);
    }

    public void leave(Guild guild) {
        AudioManager audioManager = guild.getAudioManager();
        audioManager.closeAudioConnection();
    }

    public boolean isUrl(String url) {
        return url.startsWith("http");
    }

}
