package Caffeine.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class GuildMusicManager {

    public final AudioPlayer audioPlayer;
    public final TrackScheduler scheduler;
    private final AudioPlayerSendHandler sendHandler;

    public GuildMusicManager(AudioPlayerManager manager, TextChannel channel) {
        this.audioPlayer = manager.createPlayer();
        this.scheduler = new TrackScheduler(this.audioPlayer, channel);
        this.audioPlayer.addListener(this.scheduler);
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);

        this.audioPlayer.setVolume(70);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return sendHandler;
    }
}
