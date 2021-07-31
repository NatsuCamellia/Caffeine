import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Bot {

    public static void main(String[] args) throws LoginException, InterruptedException {

        if (args.length < 1) {
            System.out.println("You have to provide TOKEN");
        }

        JDABuilder builder = JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);

        builder.setActivity(Activity.playing("調配咖啡"));

        builder.addEventListeners(new Command());

        configureMemoryUsage(builder);

        JDA jda = builder.build();
        
        jda.upsertCommand("ping", "Caculate ping of the BOT").queue();
    }

    // 調整記憶體使用量
    public static void configureMemoryUsage(JDABuilder builder) {
        // 關閉成員動態快取 (直播/遊戲/spotify)
        builder.disableCache(CacheFlag.ACTIVITY);

        // 只儲存在語音頻道內的成員或伺服器擁有者的快取
        builder.setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER));

        // Disable member chunking on startup
        builder.setChunkingFilter(ChunkingFilter.NONE);

        // Disable presence updates and typing events
        builder.disableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING);

        // Consider guilds with more than 50 members as "large".
        // Large guilds will only provide online members in their setup and thus reduce
        // bandwidth if chunking is disabled.
        builder.setLargeThreshold(50);
    }
}
