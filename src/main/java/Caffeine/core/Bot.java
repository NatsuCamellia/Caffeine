package Caffeine.core;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Bot {

    public static String prefix = "c.";

    public static void main(String[] args) throws LoginException {

        String TOKEN;

        if (args.length < 1) {
            TOKEN = System.getenv("TOKEN");
        } else {
            TOKEN = args[0];
        }

        JDABuilder.createDefault(TOKEN)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .enableCache(CacheFlag.VOICE_STATE, CacheFlag.ONLINE_STATUS)
                .setMemberCachePolicy(MemberCachePolicy.ONLINE)
                .setActivity(Activity.playing(prefix + "help"))
                .addEventListeners(new Listener()).build();
    }
}
