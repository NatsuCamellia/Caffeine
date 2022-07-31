package Caffeine.music;

import Caffeine.util.EmbedUtil;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerManager {

    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild, TextChannel channel) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager, channel);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAndPlay(Guild guild, TextChannel textChannel, String trackURL) {
        GuildMusicManager musicManager = this.getMusicManager(guild, textChannel);
        musicManager.scheduler.setTextChannel(textChannel);

        this.audioPlayerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                boolean isPlaying = musicManager.audioPlayer.getPlayingTrack() != null;
                musicManager.scheduler.queueTrack(audioTrack);
                if (isPlaying) {
                    sendMessageEmbed(textChannel, "已加入待播清單", TrackScheduler.getTrackString(audioTrack));
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                boolean isPlaying = musicManager.audioPlayer.getPlayingTrack() != null;

                // Provided with ytsearch
                if (!trackURL.startsWith("http")) {
                    musicManager.scheduler.queueTrack(audioPlaylist.getTracks().get(0));
                    if (isPlaying) {
                        sendMessageEmbed(textChannel, "已加入待播清單", TrackScheduler.getTrackString(audioPlaylist.getTracks().get(0)));
                    }
                    return;
                }

                int size = musicManager.scheduler.getQueueSize();

                List<AudioTrack> tracks = audioPlaylist.getTracks();
                if (!tracks.isEmpty()) {
                    musicManager.scheduler.queueList(tracks);
                }

                int count = musicManager.scheduler.getQueueSize() - size;
                if (!isPlaying) count++;
                sendMessageEmbed(textChannel, "已加入播放清單", "已將清單中的 " + count + " 首音樂加入播放清單");
            }

            @Override
            public void noMatches() {
                textChannel.sendMessage("找不到相符的結果").queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                e.printStackTrace();
                textChannel.sendMessage("音樂播放時發生錯誤").queue();
            }
        });
    }

    public void nextTrack(Guild guild, TextChannel channel) {
        this.getMusicManager(guild, channel).scheduler.nextTrack();
    }

    public void getQueue(Guild guild, TextChannel channel) {
        TrackScheduler scheduler = getMusicManager(guild, channel).scheduler;
        BlockingQueue<AudioTrack> queue = scheduler.getQueue();
        StringBuilder sb = new StringBuilder();

        AudioTrack np = scheduler.getAudioPlayer().getPlayingTrack();
        sb.append("**正在播放**：\n").append(TrackScheduler.getTrackString(np)).append("\n");

        sb.append("**等待播放**：\n");
        AtomicInteger count = new AtomicInteger(1);
        queue.forEach((t) -> {
            if (count.get() < 11) {
                sb.append(String.format("`%d.` %s\n", count.getAndIncrement(), TrackScheduler.getTrackString(t)));
            }
        });
        sb.append(String.format("**共 `%d` 首音樂等待播放\n**", queue.size()));
        sendMessageEmbed(channel, "播放清單", sb.toString());
    }

    public static PlayerManager getINSTANCE() {

        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    private void sendMessageEmbed(TextChannel channel, String title, String message) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(EmbedUtil.BLUE);
        builder.setTitle(title);
        builder.setDescription(message);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

}
