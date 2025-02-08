package net.natsucamellia.caffeine.service.guild;

import net.dv8tion.jda.api.entities.Guild;

public interface GuildService {
    GuildInfo getGuildInfo(Guild guild);
}