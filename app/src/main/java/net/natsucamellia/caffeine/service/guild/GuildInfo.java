package net.natsucamellia.caffeine.service.guild;

public record GuildInfo(
    String name,
    String iconUrl,
    String createdTime,
    long memberCount,
    long onlineCount
) {
}
