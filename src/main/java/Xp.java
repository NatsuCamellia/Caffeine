import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Xp extends ListenerAdapter {
    
    Map<String, Long> time = new HashMap<>();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        
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

    @SuppressWarnings("unchecked")
    public void add_xp(String user_id, String guild_id) {

        JSONObject object = new JSONObject();
        Map<String, Long> xp;

        try {
            FileReader reader = new FileReader("src/main/userdata/" + user_id + ".json");
            JSONParser parser = new JSONParser();
            object = (JSONObject)parser.parse(reader);
            xp = (HashMap<String, Long>)object.get("xp");

        } catch (Exception e) {
            System.out.println("Couldn't find userdata.");
            
            xp = new HashMap<String, Long>();
            xp.put("account", 0L);
        }
        
        if (xp.containsKey(guild_id) == false) {
            xp.put(guild_id, 0L);
        }

        xp.put(guild_id, xp.get(guild_id) + 1);
        xp.put("account", xp.get("account") + 1);

        object.put("xp", xp);

        try (FileWriter writer = new FileWriter("src/main/userdata/" + user_id + ".json")) {
            writer.write(object.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
