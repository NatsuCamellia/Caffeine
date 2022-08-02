package Caffeine.music;

import Caffeine.util.EmbedUtil;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer audioPlayer;
    private final BlockingQueue<AudioTrack> queue;
    private TextChannel channel;


    public TrackScheduler(AudioPlayer audioPlayer, TextChannel channel) {
        this.audioPlayer = audioPlayer;
        this.queue = new LinkedBlockingDeque<>();
        this.channel = channel;
    }

    public void queueTrack(AudioTrack track) {
        if (!this.audioPlayer.startTrack(track, true)) this.queue.offer(track);
    }

    public void queueList(List<AudioTrack> list) {
        list.stream().filter(track -> !this.audioPlayer.startTrack(track, true))
                .forEach(this.queue::offer);
    }

    public void nextTrack() {
        this.audioPlayer.startTrack(this.queue.poll(), false);
    }

    public BlockingQueue<AudioTrack> getQueue() {
        return this.queue;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        EmbedBuilder builder = getBuilder("正在播放", getTrackString(track));
        channel.sendMessageEmbeds(builder.build()).queue(m -> m.delete().queueAfter(track.getDuration(), TimeUnit.MILLISECONDS));
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    private EmbedBuilder getBuilder(String title, String message) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.GREEN);
        builder.setTitle(title);
        builder.setDescription(message);
        return builder;
    }

    public void setTextChannel(TextChannel channel) {
        this.channel = channel;
    }

    public static String getTrackString(AudioTrack track) {
        String title = track.getInfo().title;
        String uri = track.getInfo().uri;
        long sec = track.getInfo().length / 1000;
        String duration = String.format("%d:%02d", sec / 60, sec % 60);
        return String.format("[%s](%s) | `%s`\n", title, uri, duration);
    }

    public int getQueueSize() {
        return queue.size();
    }

}
