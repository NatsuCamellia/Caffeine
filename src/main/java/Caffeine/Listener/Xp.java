package Caffeine.Listener;
import java.util.HashMap;
import java.util.Map;

import Caffeine.Util.JsonUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Xp extends ListenerAdapter {
    
    Map<String, Long> time = new HashMap<>();

    public void onMessageReceived(MessageReceivedEvent event) {
        
        if (!event.isFromGuild()) return;
        
        String guild_id = event.getGuild().getId();
        String user_id = event.getAuthor().getId();
        Long now = System.currentTimeMillis();
        
        if (time.get(user_id) == null) {
            time.put(user_id, now);
            add_xp(user_id, guild_id);
        }

        Long lastTime = time.get(user_id);
        
        if (now - lastTime < 60000) {
            return;
        }

        add_xp(user_id, guild_id);
        time.put(user_id, now);
        
    }

    public void add_xp(String user_id, String guild_id) {

        JsonUtil json = new JsonUtil(user_id);

        json.setAccoundXp(json.getAccountXp() + 1);
        json.setGuildXp(guild_id, json.getGuildXp(guild_id) + 1);
        
        json.flush();
    }
}
