package net.natsucamellia.caffeine.service.member;

import net.dv8tion.jda.api.entities.Member;

public interface MemberService {
    MemberInfo getMemberInfo(Member member);
}

