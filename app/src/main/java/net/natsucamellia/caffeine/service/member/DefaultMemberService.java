package net.natsucamellia.caffeine.service.member;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.natsucamellia.caffeine.util.TimeUtil;

import java.util.List;

public class DefaultMemberService implements MemberService {
    @Override
    public MemberInfo getMemberInfo(Member member) {
        String account = member.getUser().getAsTag();
        String thumbnail = member.getUser().getAvatarUrl() == null ? member.getUser().getDefaultAvatarUrl() : member.getUser().getAvatarUrl();
        String nickname = member.getEffectiveName();
        String created = TimeUtil.offsetDateTimeToString(member.getTimeCreated());
        String joined = TimeUtil.offsetDateTimeToString(member.getTimeJoined());
        List<Role> roles = member.getRoles();

        return new MemberInfo(account, thumbnail, nickname, created, joined, roles);
    }
}
