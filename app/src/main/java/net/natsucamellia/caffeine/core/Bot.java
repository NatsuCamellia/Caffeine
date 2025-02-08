package net.natsucamellia.caffeine.core;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.natsucamellia.caffeine.listener.CommandListener;
import net.natsucamellia.caffeine.listener.MessageListener;
import net.natsucamellia.caffeine.service.guild.DefaultGuildService;
import net.natsucamellia.caffeine.service.member.DefaultMemberService;

public class Bot {

    public static void main(String[] args) {
        String TOKEN;

        if (args.length < 1) {
            TOKEN = System.getenv("TOKEN");
        } else {
            TOKEN = args[0];
        }

        ListenerAdapter[] listeners = {
                new MessageListener(),
                new CommandListener(
                        new DefaultMemberService(),
                        new DefaultGuildService()
                )
        };

        JDABuilder.createDefault(TOKEN)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .enableCache(CacheFlag.ONLINE_STATUS)
                .setMemberCachePolicy(MemberCachePolicy.ONLINE)
                .addEventListeners((Object[])listeners)
                .build();
    }
}