package Caffeine.listener;
import java.util.HashMap;
import java.util.Map;

import Caffeine.util.JsonUtil;

public class Xp {
    
    Map<String, Long> time = new HashMap<>();

    public void add_xp(String user_id, String guild_id) {

        Long now = System.currentTimeMillis();

        if (time.get(user_id) != null) {
            Long lastTime = time.get(user_id);
            if (now - lastTime < 60000) {
                return;
            }
        }

        JsonUtil json = new JsonUtil(user_id);
        json.setAccoundXp(json.getAccountXp() + 1);
        json.setGuildXp(guild_id, json.getGuildXp(guild_id) + 1);
        json.flush();

        time.put(user_id, now);
    }
}
