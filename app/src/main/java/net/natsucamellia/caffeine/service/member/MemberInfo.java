package net.natsucamellia.caffeine.service.member;

import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public record MemberInfo(
        String account,
        String thumbnailUrl,
        String effectiveName,
        String createdTime,
        String joinedTime,
        List<Role> roles
) {

}
