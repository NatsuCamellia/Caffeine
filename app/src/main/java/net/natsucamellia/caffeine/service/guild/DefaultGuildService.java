package net.natsucamellia.caffeine.service.guild;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.natsucamellia.caffeine.util.TimeUtil;

import java.util.Arrays;
import java.util.List;

public class DefaultGuildService implements GuildService {
    @Override
    public GuildInfo getGuildInfo(Guild guild) {
        String guildName = guild.getName();
        String icon = guild.getIconUrl();
        String created = TimeUtil.offsetDateTimeToString(guild.getTimeCreated());
        long memberCount = guild.getMemberCount();
        long onlineCount = getOnlineCount(guild);

        return new GuildInfo(guildName, icon, created, memberCount, onlineCount);
    }

    private long getOnlineCount(Guild guild) {
        List<OnlineStatus> onlineStatus = Arrays.asList(OnlineStatus.ONLINE, OnlineStatus.DO_NOT_DISTURB, OnlineStatus.IDLE);
        return guild.getMembers().stream()
                .filter((m) -> onlineStatus.contains(m.getOnlineStatus()))
                .count();
    }
}
